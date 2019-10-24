package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    var user: LiveData<Resource<User>> = MutableLiveData()
        private set

    fun loadUser(userId: String) : LiveData<Resource<User>> {
        user = liveData(Dispatchers.IO) {
            emit(Resource.Loading<User>())
            try {
                val data = repository.getUser(userId)
                emit(Resource.Success(data))
            } catch (e: HttpException) {
                emit(Resource.Error<User>(e.code(), e.response()?.message().orEmpty()))
            }
        }
        return user
    }
}