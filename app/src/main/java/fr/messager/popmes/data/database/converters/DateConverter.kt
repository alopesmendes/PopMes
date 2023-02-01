package fr.messager.popmes.data.database.converters

import androidx.room.TypeConverter
import java.time.Instant

class DateConverter {
    @TypeConverter
    fun fromEpochMilliToInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(value) }
    }

    @TypeConverter
    fun fromInstantToEpochMilli(value: Instant?): Long? {
        return value?.toEpochMilli()
    }
}