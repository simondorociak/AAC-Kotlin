package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import cz.simondorociak.apparchiteture.kotlin.android.app.ui.fragments.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeUserFragment() : UserFragment
}