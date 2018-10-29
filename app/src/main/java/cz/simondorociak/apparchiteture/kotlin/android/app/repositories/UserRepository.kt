package cz.simondorociak.apparchiteture.kotlin.android.app.repositories

import android.arch.lifecycle.LiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.api.UserWebservice
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Singleton
class UserRepository @Inject constructor(
    private val webService: UserWebservice,
    private val userDao: UserDao,
    private val executor: Executor) {

    private val MAX_REFRESH_LIMIT : Int = 1

    fun getUser(userId: String) : LiveData<User> {
        // refresh if required
        refreshUser(userId)
        return userDao.readById(userId)
    }

    private fun refreshUser(userId: String) {
        executor.execute {
            if (userDao.hasUser(userId, getLastRefreshMax()) == null) {
                webService.getUser(userId).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Timber.e("User fetch failure ${t.message}")
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Timber.d("User fetch success")
                        val user: User? = response.body()
                        user?.let {
                            executor.execute {
                                // assign when user was fetched to be able to determine whether to sync or not
                                user.lastRefresh = Date()
                                userDao.create(user)
                            }
                        }
                    }
                })
            } else Timber.d("API sync is not required")
        }
    }
    
    private fun getLastRefreshMax() : Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MINUTE, -MAX_REFRESH_LIMIT)
        return calendar.time
    }
}