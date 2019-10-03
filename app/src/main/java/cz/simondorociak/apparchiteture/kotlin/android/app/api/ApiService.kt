package cz.simondorociak.apparchiteture.kotlin.android.app.api

import androidx.lifecycle.LiveData
import cz.simondorociak.apparchiteture.kotlin.android.app.common.Resource
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
interface ApiService {

    @GET("/users/{user}")
    fun getUser(@Path("user") userId: String) : LiveData<Resource<User>>
}