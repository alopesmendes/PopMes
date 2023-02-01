package fr.messager.popmes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.messager.popmes.data.database.converters.DateConverter
import fr.messager.popmes.data.database.converters.ProtoDataConverter
import fr.messager.popmes.data.database.dao.ContactDao
import fr.messager.popmes.data.database.entities.ContactsEntity
import fr.messager.popmes.domain.model.contact.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Database(
    entities = [ContactsEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    value = [
        DateConverter::class,
        ProtoDataConverter::class,
    ]
)
abstract class PopMesDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: PopMesDatabase? = null

        fun getInstance(context: Context): PopMesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PopMesDatabase::class.java,
                    "pop_mes_database.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                (0..9).forEach {
                                    val guid = "${UUID.randomUUID()}"
                                    database.contactDao().insert(
                                        ContactsEntity(
                                            guid = guid,
                                            data = User(
                                                id = guid,
                                                firstName = "Eren $it",
                                                lastName = "Yeager $it",
                                                phoneNumber = "078183102$it",
                                            )
                                        )

                                    )
                                }
                            }
                        }
                    }
                }).build()

                INSTANCE = instance
                instance
            }
        }
    }
}