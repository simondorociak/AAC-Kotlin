package cz.simondorociak.apparchiteture.kotlin.android.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company: String,
    @Expose var lastRefresh: Date)