package cz.simondorociak.apparchiteture.kotlin.android.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import cz.simondorociak.apparchiteture.kotlin.android.app.R
import cz.simondorociak.apparchiteture.kotlin.android.app.common.NetworkStateLiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.extensions.openWirelessSettings
import dagger.android.AndroidInjection
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.longSnackbar
import timber.log.Timber

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {

        private val TAG: String = BaseActivity::class.java.name
    }

    abstract val layoutId : Int

    private var networkStateLiveData: NetworkStateLiveData? = null
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        AndroidInjection.inject(this)
        setObservers()
    }

    private fun setObservers() {
        networkStateLiveData = NetworkStateLiveData(this)
        networkStateLiveData?.observe(this, Observer { state ->
            when {
                state.isActive -> {
                    Timber.tag(TAG).d("Device as connected to network")
                    snackBar?.takeIf { it.isShown }?.apply { dismiss() }
                }
                else -> {
                    Timber.tag(TAG).d("Device as not connected to network")
                    // show snack bar to user to be able to check it
                    snackBar = contentView?.longSnackbar(getString(R.string.msg_you_are_offline), getString(R.string.text_btn_settings)) {
                        openWirelessSettings()
                    }
                }
            }
        })
    }
}