package cz.simondorociak.apparchiteture.kotlin.android.app.extensions

import android.content.Context
import android.content.Intent
import android.provider.Settings

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/
fun Context.openWirelessSettings() {
    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
}