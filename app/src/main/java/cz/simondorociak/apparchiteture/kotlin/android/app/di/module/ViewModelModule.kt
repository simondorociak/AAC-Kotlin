package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import cz.simondorociak.apparchiteture.kotlin.android.app.di.key.ViewModelKey
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels.FactoryViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun providerUserViewModel(viewModel: UserViewModel) : ViewModel

    @Binds
    abstract fun provideViewModelFactory(factory: FactoryViewModel) : ViewModelProvider.Factory
}