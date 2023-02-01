package fr.messager.popmes.data.database.dao

import androidx.room.Dao
import fr.messager.popmes.common.Constants
import fr.messager.popmes.data.database.entities.MessageEntity
import fr.messager.popmes.domain.model.message.Message

@Dao
abstract class MessageDao: BaseDao<Message, MessageEntity>(
    tableName = Constants.TABLE_MESSAGES,
) {

}