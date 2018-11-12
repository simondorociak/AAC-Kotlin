package cz.simondorociak.apparchiteture.kotlin.android.app.repositories

import android.arch.lifecycle.LiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.AppExecutors
import cz.simondorociak.apparchiteture.kotlin.android.app.api.UserWebservice
import cz.simondorociak.apparchiteture.kotlin.android.app.common.NetworkBoundResource
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Singleton
class UserRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors) {

    private val MAX_REFRESH_LIMIT : Int = 1

    fun getUser(userId: String) : LiveData<Resource<User>> {
        return object: NetworkBoundResource<User, User>(appExecutors) {

            override fun saveCallResult(result: User) {
                // set up date of user fetch
                result.lastRefresh = Date()
                userDao.create(result)
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.readById(userId)
            }

            override fun shouldFetch(data: User?): Boolean {
                // check if data of user is up to date
                return (data == null || data.lastRefresh.time < getLastRefreshMax().time)
            }

            override fun createCall(): LiveData<Resource<User>> {
                return retrofit.create(UserWebservice::class.java).getUser(userId)
            }

        }.toLiveData()
    }

    private fun getLastRefreshMax() : Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MINUTE, -MAX_REFRESH_LIMIT)
        return calendar.time
    }
}