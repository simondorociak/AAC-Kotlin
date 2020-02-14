package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import android.app.Application
import androidx.room.Room
import cz.simondorociak.apparchiteture.kotlin.android.app.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) =
        Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "app-database").build()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.getUserDao()
}