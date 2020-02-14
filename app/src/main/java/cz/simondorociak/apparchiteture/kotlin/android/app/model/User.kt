package cz.simondorociak.apparchiteture.kotlin.android.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    var name: String,
    var company: String
)