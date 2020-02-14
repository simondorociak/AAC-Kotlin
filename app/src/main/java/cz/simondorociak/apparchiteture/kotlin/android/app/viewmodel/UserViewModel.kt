package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repository.UserRepository
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(
    private val repository: UserRepository) : ViewModel() {

    var users = MediatorLiveData<Resource<List<User>>>()
        private set

    init {
        // on each init of view model load at first users from db
        users.value = Resource.Loading()
        val result = repository.getUsersFromDb()
        users.addSource(result) {
            users.value = Resource.Success(it)
            users.removeSource(result)
        }
    }
}