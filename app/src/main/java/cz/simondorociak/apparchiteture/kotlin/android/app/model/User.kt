package cz.simondorociak.apparchiteture.kotlin.android.app.model

import com.squareup.moshi.Json

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
data class User(
    val id: String,
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    val name: String,
    val company: String
)