package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors
import cz.simondorociak.apparchiteture.kotlin.android.app.api.UserWebservice
import cz.simondorociak.apparchiteture.kotlin.android.app.common.LiveDataCallAdapterFactory
import cz.simondorociak.apparchiteture.kotlin.android.app.database.AppDatabase
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.repositories.UserRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideAppExecutors() : AppExecutors = AppExecutors()

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
    fun provideUserRepository(retrofit: Retrofit, userDao: UserDao, executors: AppExecutors) : UserRepository {
        return UserRepository(retrofit, userDao, executors)
    }

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideWebservice(retrofit: Retrofit) : UserWebservice = retrofit.create(UserWebservice::class.java)
}