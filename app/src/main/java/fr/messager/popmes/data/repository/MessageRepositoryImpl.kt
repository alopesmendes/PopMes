package fr.messager.popmes.data.repository

import fr.messager.popmes.data.database.dao.MessageDao
import fr.messager.popmes.domain.repository.MessageRepository
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao
): MessageRepository {

}