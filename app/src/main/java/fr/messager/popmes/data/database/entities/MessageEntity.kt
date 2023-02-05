package fr.messager.popmes.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import fr.messager.popmes.common.Constants
import fr.messager.popmes.domain.model.message.Message
import java.time.Instant

@Entity(
    tableName = Constants.TABLE_MESSAGES,
    indices = [
        Index(
            value = [Constants.FIELD_GUID],
            unique = true,
        ),
        Index(
            value = [Constants.FIELD_DATE_TIME],
        ),
        Index(
            value = [Constants.FIELD_REFERENCE_ID],
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = ContactsEntity::class,
            parentColumns = [Constants.FIELD_GUID],
            childColumns = [Constants.FIELD_REFERENCE_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID, typeAffinity = ColumnInfo.INTEGER)
    override val id: Long = 0,
    @ColumnInfo(name = Constants.FIELD_GUID, typeAffinity = ColumnInfo.TEXT)
    override val guid: String,
    @ColumnInfo(name = Constants.FIELD_DATE_TIME, typeAffinity = ColumnInfo.INTEGER)
    override val dateTime: Instant = Instant.now(),
    @ColumnInfo(name = Constants.FIELD_DATA, typeAffinity = ColumnInfo.BLOB)
    override val data: Message?,
    @ColumnInfo(name = Constants.FIELD_REFERENCE_ID, typeAffinity = ColumnInfo.TEXT)
    val referenceId: String,
): BaseEntity<Message>
