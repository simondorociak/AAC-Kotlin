package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repository.UserRepository
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    var user: LiveData<Resource<User>> = MutableLiveData()
        private set

    fun loadUser(userId: String) {
        user = repository.getUser(userId)
    }
}