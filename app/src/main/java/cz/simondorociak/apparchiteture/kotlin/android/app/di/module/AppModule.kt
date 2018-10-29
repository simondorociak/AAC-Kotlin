package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.simondorociak.apparchiteture.kotlin.android.app.api.UserWebservice
import cz.simondorociak.apparchiteture.kotlin.android.app.database.AppDatabase
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.repositories.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideExecutor() : Executor = Executors.newSingleThreadExecutor()

    @Provides
    @Singleton
    fun provideDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "appDatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) : UserDao = db.getUserDao()

    @Provides
    @Singleton
    fun provideUserRepository(api: UserWebservice, userDao: UserDao, executor: Executor) : UserRepository {
        return UserRepository(api, userDao, executor)
    }

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideWebservice(retrofit: Retrofit) : UserWebservice = retrofit.create(UserWebservice::class.java)
}