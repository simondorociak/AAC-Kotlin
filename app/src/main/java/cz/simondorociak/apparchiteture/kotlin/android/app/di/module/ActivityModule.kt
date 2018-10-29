package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import cz.simondorociak.apparchiteture.kotlin.android.app.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity() : MainActivity
}