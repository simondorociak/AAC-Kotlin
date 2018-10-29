package cz.simondorociak.apparchiteture.kotlin.android.app.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User
import java.util.*

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Dao
interface UserDao : IDao<User, String> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun create(entity: User)

    @Query("SELECT * from User WHERE login = :id")
    override fun readById(id: String): LiveData<User>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override fun update(entity: User)

    @Delete
    override fun delete(entity: User)

    @Query("SELECT * from User WHERE login = :id AND lastRefresh > :lastRefreshMax LIMIT 1")
    fun hasUser(id: String, lastRefreshMax: Date) : User?
}