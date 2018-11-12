package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
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