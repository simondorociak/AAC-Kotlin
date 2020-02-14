package cz.simondorociak.apparchiteture.kotlin.android.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import cz.simondorociak.apparchiteture.kotlin.android.app.client.ApiService
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.extensions.parseError
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService) {

    fun getUsersFromDb() = userDao.getUsers()

    fun getUser(userId: String) : LiveData<Resource<User>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val result = apiService.getUser(userId)
                emit(Resource.Success(result))
            } catch (e: HttpException) {
                emit(Resource.Error(e.response()?.code() ?: e.code(), e.response()?.parseError()))
            }
        }
    }
}