package fr.messager.popmes.domain.repository

import fr.messager.popmes.common.Resource
import fr.messager.popmes.domain.model.message.Message

interface MessageRepository {
    /**
     * Get messages by reference id
     *
     * @param referenceId the contact guid
     * @param onMessagesChange
     * @receiver
     */
    suspend fun getMessagesByReferenceId(
        referenceId: String?,
        onMessagesChange: suspend (List<Message>) -> Unit,
    )

    /**
     * Get last messages by distinct reference id
     *
     * @param onMessagesChange
     * @receiver
     */
    suspend fun getLastMessagesByDistinctReferenceId(
        onMessagesChange: suspend (List<Message>) -> Unit,
    )

    suspend fun insertMessage(message: Message): Resource<Long>
}