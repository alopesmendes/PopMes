package fr.messager.popmes.presentation.navigation.arguments

import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.mapper.ConversationParamsToConversationParamsProto.mapTo

data class ConversationParams(
    val messages: List<Message> = emptyList(),
    val contactId: String? = null,
): NavData {
    override fun toProto(): MessageLite = this.mapTo()
}
