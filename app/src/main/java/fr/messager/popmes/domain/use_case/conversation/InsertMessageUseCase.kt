package fr.messager.popmes.domain.use_case.conversation

import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.repository.MessageRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        message: Message,
    ) {
        messageRepository.insertMessage(message)
    }
}