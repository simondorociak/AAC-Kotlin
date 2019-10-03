package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.simondorociak.apparchiteture.kotlin.android.app.di.ViewModelKey
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel.FactoryViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel.UserViewModel
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
    internal abstract fun providerUserViewModel(viewModel: UserViewModel) : ViewModel

    @Binds
    internal abstract fun provideViewModelFactory(factory: FactoryViewModel) : ViewModelProvider.Factory
}