package cz.simondorociak.apparchiteture.kotlin.android.app.extensions

import retrofit2.Response

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/

fun <T> Response<T>.parseError() : String {
    val body = errorBody()?.string()
    return when {
        !body.isNullOrBlank() -> {
            // TODO parse error message from body according to error of used API model
            message()
        }
        else -> message()
    }
}