package fr.messager.popmes.domain.model.message

sealed class MessageType {
    object Camera: MessageType()

    object Photo: MessageType()

    object File: MessageType()

    object Vocal: MessageType()

    object Message: MessageType()

    object Survey: MessageType()

    object GIF: MessageType()
}
