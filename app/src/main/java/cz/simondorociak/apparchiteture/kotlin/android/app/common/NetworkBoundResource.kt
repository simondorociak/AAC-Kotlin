package cz.simondorociak.apparchiteture.kotlin.android.app.common

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors

/**
 * A generic class that can provide a resource backed by both the database and the network.<br />
 * You can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
 */
abstract class NetworkBoundResource<ResultType> @MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        // send immediate loading event
        result.value = Resource.loading()
        fetchFromNetwork()
    }

    fun toLiveData() : LiveData<Resource<ResultType>> = result

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            // detach local observers
            result.removeSource(apiResponse)
            // process response
            response?.apply {
                if (status.isSuccessful()) {
                    appExecutors.getDiskIO().execute {
                        // save data to db
                        data?.let {requestType -> saveCallResult(requestType) }
                        appExecutors.getMainThread().execute {
                            setValue(Resource.success(data))
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
    abstract fun saveCallResult(result: ResultType)

    @MainThread
    abstract fun createCall() : LiveData<Resource<ResultType>>
}