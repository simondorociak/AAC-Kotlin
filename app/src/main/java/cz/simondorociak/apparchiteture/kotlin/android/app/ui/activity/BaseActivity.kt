package cz.simondorociak.apparchiteture.kotlin.android.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.simondorociak.apparchiteture.kotlin.android.app.common.NetworkStateLiveData
import dagger.android.AndroidInjection

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {

        private val TAG: String = BaseActivity::class.java.name
    }

    abstract val layoutId : Int

    private var networkStateLiveData: NetworkStateLiveData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        AndroidInjection.inject(this)
    }
}