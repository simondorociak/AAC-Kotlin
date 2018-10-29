package cz.simondorociak.apparchiteture.kotlin.android.app.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import cz.simondorociak.apparchiteture.kotlin.android.app.database.converter.DateConverter
import cz.simondorociak.apparchiteture.kotlin.android.app.database.dao.UserDao
import cz.simondorociak.apparchiteture.kotlin.android.app.database.entities.User

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
@Database(entities = [User::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

}