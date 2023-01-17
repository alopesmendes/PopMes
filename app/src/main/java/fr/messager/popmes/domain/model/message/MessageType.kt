package fr.messager.popmes.domain.model.message

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MessageType : Parcelable {
    object Camera: MessageType()

    object Photo: MessageType()

    object File: MessageType()

    object Vocal: MessageType()

    object MessageData: MessageType()

    object Survey: MessageType()

    object GIF: MessageType()
}
