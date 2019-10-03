package cz.simondorociak.apparchiteture.kotlin.android.app.common.extensions

import android.text.TextUtils
import com.google.gson.Gson
import cz.simondorociak.apparchiteture.kotlin.android.app.client.response.BaseResponse
import cz.simondorociak.apparchiteture.kotlin.android.app.client.response.ErrorBody
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Constants
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException

/**
 * Synthetic sugaring to create Retrofit Service.
 */
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Converts Retrofit [Response] to [Resource] which provides state and data to the UI.
 */
fun <T> Response<T>.toResource(): Resource<T> {
    val error = errorBody()?.toString() ?: message()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body, code())
                code() == Constants.Retrofit.NO_CONTENT -> Resource.success(code = code())
                else -> Resource.error(error, code())
            }
        } else -> Resource.error(parseError(this).error?.message, code())
    }
}

fun <T> parseError(response: Response<T>): BaseResponse {
    var body: String? = null
    try {
        body = response.errorBody()?.string()
    } catch (e: NullPointerException) {
        Timber.d("NullPointerException: ${e.message}")
    } catch (e: IOException) {
        Timber.d("IOException: ${e.message}")
    }
    var result: BaseResponse? = null
    try {
        result = Gson().fromJson<BaseResponse>(body, BaseResponse::class.java)
    } catch (e: Exception) {
        if (!TextUtils.isEmpty(body)) {
            result = BaseResponse(false, ErrorBody(response.code(), "Unknown error"))
        }
    }

    return result!!
}