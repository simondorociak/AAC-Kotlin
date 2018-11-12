package cz.simondorociak.apparchiteture.kotlin.android.app.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Entity(tableName = "User")
data class User(
    @PrimaryKey @SerializedName("id") val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company: String,
    @Expose var lastRefresh: Date)