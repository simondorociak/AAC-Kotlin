package cz.simondorociak.apparchiteture.kotlin.android.app.common

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class Resource<out T>(val status: Status, val data: T?, val code: Int = 9999, val message: String? = null) {

    companion object {

        fun <T> offline() : Resource<T> {
            return Resource(Status.OFFLINE, null)
        }

        fun <T> loading() : Resource<T> {
            return Resource(Status.LOADING, null)
        }

        fun <T> success(data: T? = null, code: Int = 200) : Resource<T> {
            return Resource(Status.SUCCESS, data, code)
        }

        fun <T> error(message: String?, code: Int) : Resource<T> {
            return Resource(Status.ERROR, null, code, message)
        }
    }
}