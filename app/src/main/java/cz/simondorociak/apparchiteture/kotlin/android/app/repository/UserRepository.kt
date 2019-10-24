package cz.simondorociak.apparchiteture.kotlin.android.app.repository

import cz.simondorociak.apparchiteture.kotlin.android.app.client.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService) {

    suspend fun getUser(userId: String) = apiService.getUser(userId)
}