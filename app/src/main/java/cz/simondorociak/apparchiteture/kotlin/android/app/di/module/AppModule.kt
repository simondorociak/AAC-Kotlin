package cz.simondorociak.apparchiteture.kotlin.android.app.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors
import cz.simondorociak.apparchiteture.kotlin.android.app.BuildConfig
import cz.simondorociak.apparchiteture.kotlin.android.app.client.ApiService
import cz.simondorociak.apparchiteture.kotlin.android.app.common.LiveDataCallAdapterFactory
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
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}