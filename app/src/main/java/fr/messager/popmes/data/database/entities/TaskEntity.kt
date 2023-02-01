package fr.messager.popmes.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import fr.messager.popmes.common.Constants
import fr.messager.popmes.domain.model.task.Task
import java.time.Instant

@Entity(
    tableName = Constants.TABLE_TASKS,
    indices = [
        Index(
            value = [Constants.FIELD_GUID],
            unique = true,
        ),
        Index(
            value = [Constants.FIELD_DATE_TIME],
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.FIELD_ID, typeAffinity = ColumnInfo.INTEGER)
    override val id: Long = 0,
    @ColumnInfo(name = Constants.FIELD_GUID, typeAffinity = ColumnInfo.TEXT)
    override val guid: String,
    @ColumnInfo(name = Constants.FIELD_DATE_TIME, typeAffinity = ColumnInfo.INTEGER)
    override val dateTime: Instant = Instant.now(),
    @ColumnInfo(name = Constants.FIELD_DATA, typeAffinity = ColumnInfo.BLOB)
    override val data: Task?
): BaseEntity<Task>