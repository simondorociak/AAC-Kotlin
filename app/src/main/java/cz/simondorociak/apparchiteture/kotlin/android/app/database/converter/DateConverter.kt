package cz.simondorociak.apparchiteture.kotlin.android.app.database.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class DateConverter {

    @TypeConverter
    fun toDate(time: Long?) : Date? {
        time?.let {
            return Date(time)
        }
        return null
    }

    @TypeConverter
    fun toTimestamp(date: Date?) : Long? {
        date?.let {
            return date.time
        }
        return null
    }
}