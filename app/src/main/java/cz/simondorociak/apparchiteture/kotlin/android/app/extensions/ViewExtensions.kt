package cz.simondorociak.apparchiteture.kotlin.android.app.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/

fun View.snackbar(msg: CharSequence, duration: Int) = Snackbar
    .make(this, msg, duration)
    .show()

fun View.snackbar(msgResId: Int, duration: Int) = snackbar(context.getString(msgResId), duration)

fun View.snackbar(msg: CharSequence) = snackbar(msg, Snackbar.LENGTH_SHORT)

fun View.snackbar(msgResId: Int) = snackbar(msgResId, Snackbar.LENGTH_SHORT)