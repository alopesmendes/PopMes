package fr.messager.popmes.domain.use_case.conversation

import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.repository.MessageRepository
import javax.inject.Inject

class GetLastMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        onLastMessagesChange: suspend (List<Message>) -> Unit,
    ) {
        messageRepository.getLastMessagesByDistinctReferenceId(onLastMessagesChange)
    }
}