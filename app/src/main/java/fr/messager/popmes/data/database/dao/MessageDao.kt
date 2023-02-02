package fr.messager.popmes.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import fr.messager.popmes.common.Constants.FIELD_DATA
import fr.messager.popmes.common.Constants.FIELD_DATE_TIME
import fr.messager.popmes.common.Constants.FIELD_REFERENCE_ID
import fr.messager.popmes.common.Constants.TABLE_MESSAGES
import fr.messager.popmes.data.database.entities.MessageEntity
import fr.messager.popmes.domain.model.message.Message
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MessageDao: BaseDao<Message, MessageEntity>(
    tableName = TABLE_MESSAGES,
) {
    @Query("select $FIELD_DATA from $TABLE_MESSAGES " +
            "group by $FIELD_REFERENCE_ID " +
            "order by $FIELD_DATE_TIME desc")
    abstract fun findLastMessagesGroupByReferenceId(): Flow<List<Message?>>

    @Query("select $FIELD_DATA from $TABLE_MESSAGES " +
            "where $FIELD_REFERENCE_ID = :referenceId " +
            "order by $FIELD_DATE_TIME desc")
    abstract fun findMessagesByReferenceId(referenceId: String?): Flow<List<Message?>>
}