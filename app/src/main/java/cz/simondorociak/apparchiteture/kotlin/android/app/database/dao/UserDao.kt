package cz.simondorociak.apparchiteture.kotlin.android.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/
@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUsers() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll()
}