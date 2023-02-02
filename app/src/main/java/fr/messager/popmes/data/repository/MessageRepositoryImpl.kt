package fr.messager.popmes.data.repository

import android.content.Context
import android.util.Log
import fr.messager.popmes.R
import fr.messager.popmes.common.Resource
import fr.messager.popmes.data.database.dao.MessageDao
import fr.messager.popmes.data.database.entities.MessageEntity
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.repository.MessageRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val context: Context,
): MessageRepository {

    companion object {
        private val TAG = MessageRepositoryImpl::class.simpleName
    }
    override suspend fun getMessagesByReferenceId(
        referenceId: String?,
        onMessagesChange: suspend (List<Message>) -> Unit
    ) {
        Log.d(TAG, "getMessagesByReferenceId: $referenceId")
        messageDao
            .findMessagesByReferenceId(referenceId)
            .map { value -> value.filterNotNull() }
            .collect {
                Log.d(TAG, "getMessagesByReferenceId: ${it.size}")
                onMessagesChange(it)
            }
    }

    override suspend fun getLastMessagesByDistinctReferenceId(onMessagesChange: suspend (List<Message>) -> Unit) {
        Log.d(TAG, "getLastMessagesByDistinctReferenceId: ")
        messageDao
            .findLastMessagesGroupByReferenceId()
            .map { value -> value.filterNotNull() }
            .collect(onMessagesChange)
    }

    override suspend fun insertMessage(message: Message): Resource<Long> {
        val index = messageDao.insert(
            MessageEntity(
                referenceId = message.to.id,
                guid = message.id,
                data = message,
            )
        )

        return if (index == -1L)
            Resource.Error(message = context.getString(R.string.error_insert_message))
        else
            Resource.Success(data = index)
    }

}