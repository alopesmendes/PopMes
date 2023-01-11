package fr.messager.popmes.domain.model.message

import fr.messager.popmes.domain.model.contact.Contact

data class Message(
    val id: String,
    val messageType: MessageType,
    val from: Contact,
    val to: Contact,
)
