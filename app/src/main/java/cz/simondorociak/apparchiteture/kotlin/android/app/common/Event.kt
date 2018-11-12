package cz.simondorociak.apparchiteture.kotlin.android.app.common

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // allow external read but not write

    fun getContentIfNotHandled() : T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() : T = content
}