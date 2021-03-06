package cz.simondorociak.apparchiteture.kotlin.android.app.di.key

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value : KClass<out ViewModel>)