package cz.simondorociak.apparchiteture.kotlin.android.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import cz.simondorociak.apparchiteture.kotlin.android.app.client.ApiService
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
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
    private val apiService: ApiService) {

    fun getUser(userId: String) : LiveData<Resource<User>> = liveData(Dispatchers.IO) {
        emit(Resource.Loading<User>())
        try {
            val data = apiService.getUser(userId)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error<User>(e.code(), e.response()?.message().orEmpty()))
        }
    }
}