package cz.simondorociak.apparchiteture.kotlin.android.app.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors

/**
 * A generic class that can provide a resource backed by both the database and the network.<br />
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        // sent to recipient loading event at first
        result.value = Resource.loading()
        @Suppress("leakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            // case when data should be refreshed
            if (shouldFetch(it)) {
                fetchFromNetwork(dbSource)
            } else {
                // just load data from db because sync is not required
                result.addSource(dbSource) { result -> setValue(Resource.success(result)) }
            }
        }
    }

    fun toLiveData() : LiveData<Resource<ResultType>> = result

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // re-attach observer to bound loading state to UI
        result.addSource(dbSource) { setValue(Resource.loading()) }
        result.addSource(apiResponse) { response ->
            // detach local observers
            result.removeSource(dbSource)
            result.removeSource(apiResponse)
            // process response
            response?.apply {
                if (status.isSuccessful()) {
                    appExecutors.getDiskIO().execute {
                        // save data to db
                        data?.let {requestType -> saveCallResult(requestType) }
                        // load latest fetched data from database to keep rule of "single source of truth"
                        appExecutors.getMainThread().execute {
                            result.addSource(loadFromDb()) { data -> setValue(Resource.success(data)) }
                        }
                    }
                } else {
                    // show error
                    setValue(Resource.error(message, code))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @WorkerThread
    abstract fun saveCallResult(result: RequestType)

    @MainThread
    abstract fun loadFromDb() : LiveData<ResultType>

    @MainThread
    abstract fun shouldFetch(data: ResultType?) : Boolean

    @MainThread
    abstract fun createCall() : LiveData<Resource<RequestType>>
}