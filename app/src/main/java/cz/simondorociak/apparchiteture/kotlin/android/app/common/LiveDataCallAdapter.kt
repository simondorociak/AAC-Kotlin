package cz.simondorociak.apparchiteture.kotlin.android.app.common

import android.arch.lifecycle.LiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.common.extensions.toResource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <P>
 */
private class LiveDataCallAdapter<P>(private val responseType: Type) : CallAdapter<P, LiveData<Resource<P>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<P>): LiveData<Resource<P>> {
        return object : LiveData<Resource<P>>() {

            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<P> {
                        override fun onResponse(call: Call<P>, response: Response<P>) {
                            postValue(response.toResource())
                        }

                        override fun onFailure(call: Call<P>, throwable: Throwable) {
                            postValue(Resource.error(throwable.message, Constants.Retrofit.ERROR_UNKNOWN))
                            throwable.printStackTrace()
                        }
                    })
                }
            }
        }
    }
}