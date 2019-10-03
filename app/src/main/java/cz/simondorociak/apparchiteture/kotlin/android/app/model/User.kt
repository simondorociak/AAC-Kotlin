package cz.simondorociak.apparchiteture.kotlin.android.app.model

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
data class User(
    val id: String,
    val login: String,
    val avatarUrl: String,
    val name: String,
    val company: String
)