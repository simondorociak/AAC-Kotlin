package cz.simondorociak.apparchiteture.kotlin.android.app.client

import cz.simondorociak.apparchiteture.kotlin.android.app.model.User
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
interface ApiService {

    @GET("/users/{user}")
    suspend fun getUser(@Path("user") userId: String) : User
}