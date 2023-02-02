package fr.messager.popmes.domain.use_case.conversation

import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private var messageRepository: MessageRepository
) {
    suspend operator fun invoke(
        selectedContact: Contact?,
        onMessagesChange: suspend (List<Message>) -> Unit,
    ) {
        messageRepository.getMessagesByReferenceId(
            referenceId = selectedContact?.id,
            onMessagesChange = onMessagesChange,
        )
    }
}