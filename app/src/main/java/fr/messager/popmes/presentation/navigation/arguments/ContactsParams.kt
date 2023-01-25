package fr.messager.popmes.presentation.navigation.arguments

import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.mapper.ContactParamsToContactParamsProto.mapTo

data class ContactsParams(
    val users: List<User>,
    val toAddUserComponentVisibility: Boolean,
    val toAddGroupComponentVisibility: Boolean,
): NavData {
    override fun toProto(): MessageLite = this.mapTo()
}
