package cz.simondorociak.apparchiteture.kotlin.android.app.common

/**
 * Represents commonly used http response constants.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
object HttpCodes {

    const val HTTP_OK = 200
    const val HTTP_NO_CONTENT = 204
    const val HTTP_UNAUTHORIZED = 401
    const val HTTP_SERVER_ERROR = 501
    const val HTTP_ERROR_TIMEOUT = 408
    const val HTTP_SERVICE_UNAVAILABLE = 503
    const val HTTP_UNKNOWN = 999
    const val HTTP_NO_INTERNET_CONNECTION = 10009
    const val HTTP_ERROR_OTHER = 10004
}