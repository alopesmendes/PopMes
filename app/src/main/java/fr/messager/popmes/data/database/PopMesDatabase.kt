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
import fr.messager.popmes.data.database.dao.MessageDao
import fr.messager.popmes.data.database.dao.TaskDao
import fr.messager.popmes.data.database.entities.ContactsEntity
import fr.messager.popmes.data.database.entities.MessageEntity
import fr.messager.popmes.data.database.entities.TaskEntity
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID

@Database(
    entities = [
        ContactsEntity::class,
        MessageEntity::class,
        TaskEntity::class,
    ],
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

    abstract fun messageDao(): MessageDao

    abstract fun taskDao(): TaskDao

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
                                val currentUser = ContactsEntity(
                                    guid = "0",
                                    data = User(
                                        id = "0",
                                        firstName = "Ailton",
                                        lastName = "Lopes Mendes",
                                        phoneNumber = "+33781831024",
                                        description = "Blue lock \uD83D\uDD25 \uD83D\uDD25",
                                    )
                                )
                                val contacts = (0..9).map {
                                    val guid = "${UUID.randomUUID()}"
                                    ContactsEntity(
                                        guid = guid,
                                        data = User(
                                            id = guid,
                                            firstName = "Eren $it",
                                            lastName = "Yeager $it",
                                            phoneNumber = "078183102$it",
                                            description = "user $it here for problems and make it double"
                                        )
                                    )
                                }

                                val messages = (0..15).map { index ->
                                    val guid = "${UUID.randomUUID()}"
                                    val from = if (index % 5 == 0) currentUser else contacts.random()
                                    val to = if (from.guid == currentUser.guid) contacts.random() else currentUser
                                    val dateTime = Instant.now()
                                    val destination = if (to.guid == currentUser.guid) from else to

                                    MessageEntity(
                                        guid = guid,
                                        referenceId = destination.guid,
                                        dateTime = dateTime,
                                        data = Message(
                                            id = guid,
                                            messageType = MessageType.MessageData(
                                                text = "Bonjour ${to.data!!.fullName()} je voulais dire que One Piece est une série de shōnen mangas créée par Eiichirō Oda. Elle est prépubliée depuis le 22 juillet 1997 dans le magazine hebdomadaire Weekly Shōnen Jump, puis regroupée en tankōbon aux éditions Shūeisha depuis le 24 décembre 1997. 104 tomes sont commercialisés au Japon en novembre 2022"
                                            ),
                                            from = from.data as User,
                                            to = to.data,
                                            date = dateTime,
                                            destination = destination.data!!,
                                        )
                                    )
                                }

                                database
                                    .contactDao()
                                    .insert(*contacts.toTypedArray(), currentUser)
                                    .also {
                                        database.messageDao().insert(*messages.toTypedArray())
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