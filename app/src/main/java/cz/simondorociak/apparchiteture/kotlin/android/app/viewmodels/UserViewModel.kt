package cz.simondorociak.apparchiteture.kotlin.android.app.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import cz.simondorociak.apparchiteture.kotlin.android.app.repositories.UserRepository
import javax.inject.Inject

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var user: LiveData<User> = MutableLiveData()

    fun init(userId: String) {
        user = repository.getUser(userId)
    }

    fun getUser() : LiveData<User> = user
}