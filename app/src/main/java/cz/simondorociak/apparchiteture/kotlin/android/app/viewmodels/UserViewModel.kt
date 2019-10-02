package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repositories.UserRepository
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var user: LiveData<Resource<User>> = MutableLiveData()

    fun getUser(userId: String) : LiveData<Resource<User>> {
        user = repository.getUser(userId)
        return user
    }

}