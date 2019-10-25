package cz.simondorociak.apparchiteture.kotlin.android.app.common

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
sealed class Resource<T>(val data: T? = null, val code: Int = HttpCodes.HTTP_UNKNOWN, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data, HttpCodes.HTTP_OK)

    class Error<T>(code: Int, message: String?) : Resource<T>(code = code, message = message)

    class Loading<T> : Resource<T>()
}