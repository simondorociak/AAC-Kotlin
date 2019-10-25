package cz.simondorociak.apparchiteture.kotlin.android.app.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import timber.log.Timber

/**
 * Helper class used to monitor current network connection state and its type (Wi-Fi/Cellular).
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/
class NetworkStateLiveData(context: Context?) : LiveData<NetworkStateLiveData.NetworkState>() {

    class NetworkState(val isActive: Boolean, private val connectedNetworkType: Int = -1) {

        fun isCellular() = connectedNetworkType == NetworkCapabilities.TRANSPORT_CELLULAR
    }

    companion object {

        private val TAG: String = NetworkStateLiveData::class.java.name
    }

    private var cm: ConnectivityManager? = null
    private var cmCallback: ConnectivityManager.NetworkCallback? = null

    init {
        cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        cmCallback = object: ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                Timber.tag(TAG).d("onLost invoked")
                postValue(NetworkState(false))
            }

            override fun onAvailable(network: Network) {
                Timber.tag(TAG).d("onAvailable invoked")
                postValue(NetworkState(true, getConnectedNetworkType()))
            }
        }
    }

    @Suppress("deprecation")
    private fun getConnectedNetworkType() : Int {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                cm?.activeNetwork?.let {
                    cm?.getNetworkCapabilities(it)?.let { capabilities ->
                        return when {
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkCapabilities.TRANSPORT_WIFI
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkCapabilities.TRANSPORT_CELLULAR
                            else -> -1
                        }
                    }
                }
            }
            else -> {
                val wifiInfo = cm?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                val mobileInfo = cm?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                return when {
                    (wifiInfo?.isConnected ?: false) -> NetworkCapabilities.TRANSPORT_WIFI
                    (mobileInfo?.isConnected ?: false) -> NetworkCapabilities.TRANSPORT_CELLULAR
                    else -> -1
                }
            }
        }
        return -1
    }

    @Suppress("deprecation")
    override fun onActive() {
        // send immediately current connection state to observer
        cm?.let {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    cm?.activeNetwork?.let {
                        cm?.getNetworkCapabilities(it)?.let { capabilities ->
                            val isActive = capabilities.hasTransport(
                                NetworkCapabilities.TRANSPORT_WIFI)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            postValue(NetworkState(isActive, getConnectedNetworkType()))
                        } ?: postValue(NetworkState(false))
                    } ?: postValue(NetworkState(false))
                }
                else -> {
                    postValue(NetworkState(cm?.activeNetworkInfo?.isConnected ?: false, getConnectedNetworkType()))
                }
            }
        }
        // register network callback
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                cm?.let { manager -> cmCallback?.let { callback -> manager.registerDefaultNetworkCallback(callback) } }
            }
            else -> {
                val request = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build()
                cm?.let { manager -> cmCallback?.let { callback -> manager.registerNetworkCallback(request, callback) } }
            }
        }
    }

    override fun onInactive() {
        cm?.let { manager -> cmCallback?.let { callback -> manager.unregisterNetworkCallback(callback) } }
    }
}