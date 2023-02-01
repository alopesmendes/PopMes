package fr.messager.popmes.domain.model.message

import fr.messager.popmes.domain.model.ProtoData
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.mapper.MessageToMessageProto.mapTo
import java.time.Instant


data class Message(
    val id: String,
    val messageType: MessageType,
    val from: User,
    val to: Contact,
    val date: Instant,
): ProtoData {
    override fun toProto() = this.mapTo()
}
