package cz.simondorociak.apparchiteture.kotlin.android.app.di.component

import android.app.Application
import cz.simondorociak.apparchiteture.kotlin.android.app.App
import cz.simondorociak.apparchiteture.kotlin.android.app.di.module.ActivityModule
import cz.simondorociak.apparchiteture.kotlin.android.app.di.module.NetworkModule
import cz.simondorociak.apparchiteture.kotlin.android.app.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Component(modules = [AndroidSupportInjectionModule::class, NetworkModule::class, ActivityModule::class, FragmentModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }

    fun inject(app: App)
}