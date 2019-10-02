package cz.simondorociak.apparchiteture.kotlin.android.app.common

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [callback] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val callback : (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandled()?.let { value -> callback(value) }
    }
}