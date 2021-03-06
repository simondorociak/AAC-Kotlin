package cz.simondorociak.apparchiteture.kotlin.android.app.api

import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
interface UserWebservice {

    @GET("/users/{user}")
    fun getUser(@Path("user") userId: String) : Call<User>
}