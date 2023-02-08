package fr.messager.popmes.presentation.navigation.arguments

import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.mapper.ContactParamsToContactParamsProto.mapTo

data class ContactsParams(
    val selectOrCreateContact: Contact,
    val toAddUserComponentVisibility: Boolean,
    val toAddGroupComponentVisibility: Boolean,
): NavData {
    override fun toProto(): MessageLite = this.mapTo()
}
