package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel

import androidx.lifecycle.*
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.extensions.parseError
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    var user = MediatorLiveData<Resource<User>>()
        private set

    fun loadUser(userId: String) {
        val result = repository.getUser(userId)
        user.addSource(result) {
            user.value = it
            if (it is Resource.Success) {
                user.removeSource(result)
            }
        }
    }
}