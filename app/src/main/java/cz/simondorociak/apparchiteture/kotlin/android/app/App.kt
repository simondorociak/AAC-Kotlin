package cz.simondorociak.apparchiteture.kotlin.android.app

import android.app.Activity
import android.app.Application
import cz.simondorociak.apparchiteture.kotlin.android.app.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityInjector
    }
}