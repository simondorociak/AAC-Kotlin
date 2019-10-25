package cz.simondorociak.apparchiteture.kotlin.android.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        AndroidInjection.inject(this)
    }
}