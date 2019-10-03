package cz.simondorociak.apparchiteture.kotlin.android.app.repositories

import androidx.lifecycle.LiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors
import cz.simondorociak.apparchiteture.kotlin.android.app.api.ApiService
import cz.simondorociak.apparchiteture.kotlin.android.app.common.NetworkBoundResource
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Singleton
class UserRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val appExecutors: AppExecutors) {

    fun getUser(userId: String) : LiveData<Resource<User>> {
        return object: NetworkBoundResource<User>(appExecutors) {

            override fun saveCallResult(result: User) { }

            override fun createCall(): LiveData<Resource<User>> {
                return retrofit.create(ApiService::class.java).getUser(userId)
            }
        }.toLiveData()
    }
}