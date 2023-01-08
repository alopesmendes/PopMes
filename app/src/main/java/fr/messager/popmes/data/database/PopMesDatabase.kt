package fr.messager.popmes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PopMesDatabase: RoomDatabase() {
}