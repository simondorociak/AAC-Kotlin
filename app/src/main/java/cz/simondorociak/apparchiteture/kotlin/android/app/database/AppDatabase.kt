package cz.simondorociak.apparchiteture.kotlin.android.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.model.User

/**
 * @author: Simon Dorociak <S.Dorociak@gmail.com>
 **/
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

}