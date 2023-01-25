package fr.messager.popmes.mapper

import com.google.protobuf.Timestamp
import com.google.protobuf.timestamp
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import fr.messager.popmes.mapper.ContactToContactProto.mapTo
import fr.messager.popmes.mapper.ContactToContactProto.reverseMapTo
import fr.messager.popmes.mapper.GroupToGroupProto.mapTo
import fr.messager.popmes.mapper.GroupToGroupProto.reverseMapTo
import fr.messager.popmes.mapper.InstantToTimestamp.mapTo
import fr.messager.popmes.mapper.InstantToTimestamp.reverseMapTo
import fr.messager.popmes.mapper.MessageToMessageProto.mapTo
import fr.messager.popmes.mapper.MessageToMessageProto.reverseMapTo
import fr.messager.popmes.mapper.MessageTypeToMessageTypeProto.mapTo
import fr.messager.popmes.mapper.MessageTypeToMessageTypeProto.reverseMapTo
import fr.messager.popmes.mapper.UserToUserProto.mapTo
import fr.messager.popmes.mapper.UserToUserProto.reverseMapTo
import fr.messager.popmes.presentation.navigation.arguments.ContactsParams
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams
import fr.messager.popmes.proto.ContactProto
import fr.messager.popmes.proto.ContactsParamsProto
import fr.messager.popmes.proto.ConversationParamsProto
import fr.messager.popmes.proto.GroupProto
import fr.messager.popmes.proto.MessageProto
import fr.messager.popmes.proto.MessageTypeProto
import fr.messager.popmes.proto.UserProto
import fr.messager.popmes.proto.contactProto
import fr.messager.popmes.proto.contactsParamsProto
import fr.messager.popmes.proto.conversationParamsProto
import fr.messager.popmes.proto.groupProto
import fr.messager.popmes.proto.messageDataProto
import fr.messager.popmes.proto.messageProto
import fr.messager.popmes.proto.messageTypeProto
import fr.messager.popmes.proto.userProto
import java.time.Instant

object UserToUserProto: ReverseMapper<User, UserProto> {
    override fun User.mapTo(): UserProto = userProto {
        id = this@mapTo.id
        firstName = this@mapTo.firstName
        lastName = this@mapTo.lastName
        phoneNumber = this@mapTo.phoneNumber
    }

    override fun UserProto.reverseMapTo(): User = User(
        id = this@reverseMapTo.id,
        firstName = this@reverseMapTo.firstName,
        lastName = this@reverseMapTo.lastName,
        phoneNumber = this@reverseMapTo.phoneNumber,
    )
}

object GroupToGroupProto: ReverseMapper<Group, GroupProto> {
    override fun Group.mapTo(): GroupProto = groupProto {
        id = this@mapTo.id
        users.addAll(this@mapTo.users.map { it.mapTo() })
        name = this@mapTo.name
    }

    override fun GroupProto.reverseMapTo(): Group = Group(
        id = this@reverseMapTo.id,
        name = this@reverseMapTo.name,
        users = this@reverseMapTo.usersList.map { it.reverseMapTo() },
    )
}



object ContactToContactProto: Mapper<ContactProto, Contact> {
    override fun ContactProto.mapTo(): Contact {
        return when(this@mapTo.innerMessageCase) {
            ContactProto.InnerMessageCase.USER -> this@mapTo.user.reverseMapTo()
            ContactProto.InnerMessageCase.GROUP -> this@mapTo.group.reverseMapTo()

            ContactProto.InnerMessageCase.INNERMESSAGE_NOT_SET -> throw IllegalArgumentException("contact proto not set")
            else -> throw IllegalArgumentException("contact proto undefined")
        }
    }
    fun <T: Contact> T.reverseMapTo(): ContactProto = contactProto {
        when (this@reverseMapTo) {
            is User -> { user = this@reverseMapTo.mapTo() }
            is Group -> { group = this@reverseMapTo.mapTo() }
        }
    }
}

object MessageTypeToMessageTypeProto: ReverseMapper<MessageType, MessageTypeProto> {
    override fun MessageType.mapTo(): MessageTypeProto = messageTypeProto {
        when (this@mapTo) {
            is MessageType.MessageData -> { messageData = messageDataProto {  } }

            // TODO to add rest
            else -> {}
        }
    }

    override fun MessageTypeProto.reverseMapTo(): MessageType {
        return when(this@reverseMapTo.innerMessageCase) {
            MessageTypeProto.InnerMessageCase.MESSAGE_DATA -> MessageType.MessageData

            // TODO to add rest
            else -> throw IllegalArgumentException("contact proto not set")
        }
    }
}


object InstantToTimestamp: ReverseMapper<Instant, Timestamp> {
    override fun Instant.mapTo(): Timestamp = timestamp {
        nanos = this@mapTo.nano
        seconds = this@mapTo.epochSecond
    }

    override fun Timestamp.reverseMapTo(): Instant {
        return if (this@reverseMapTo.serializedSize != 0)
            Instant.ofEpochSecond(
                this@reverseMapTo.seconds,
                this@reverseMapTo.nanos.toLong()
            )
        else
            Instant.now()
    }
}

object MessageToMessageProto: ReverseMapper<Message, MessageProto> {
    override fun Message.mapTo(): MessageProto = messageProto {
        id = this@mapTo.id
        type = this@mapTo.messageType.mapTo()
        date = this@mapTo.date.mapTo()
        from = this@mapTo.from.mapTo()
        to = this@mapTo.to.reverseMapTo()
    }

    override fun MessageProto.reverseMapTo(): Message = Message(
        id = this@reverseMapTo.id,
        messageType = this@reverseMapTo.type.reverseMapTo(),
        date = this@reverseMapTo.date.reverseMapTo(),
        to = this@reverseMapTo.to.mapTo(),
        from = this@reverseMapTo.from.reverseMapTo(),
    )
}

object ConversationParamsToConversationParamsProto: ReverseMapper<ConversationParams, ConversationParamsProto> {
    override fun ConversationParams.mapTo(): ConversationParamsProto = conversationParamsProto {
        messages.addAll(this@mapTo.messages.map { it.mapTo() })
        if (this@mapTo.contact != null)
            contact = this@mapTo.contact.reverseMapTo()
    }

    override fun ConversationParamsProto.reverseMapTo(): ConversationParams = ConversationParams(
        messages = this@reverseMapTo.messagesList.map { it.reverseMapTo() },
        contact = this@reverseMapTo.contact.mapTo(),
    )
}

object ContactParamsToContactParamsProto: ReverseMapper<ContactsParams, ContactsParamsProto> {
    override fun ContactsParams.mapTo(): ContactsParamsProto = contactsParamsProto {
        users.addAll(this@mapTo.users.map { it.mapTo() })
        toAddUserComponentVisibility = this@mapTo.toAddUserComponentVisibility
        toAddGroupComponentVisibility = this@mapTo.toAddGroupComponentVisibility
    }

    override fun ContactsParamsProto.reverseMapTo(): ContactsParams = ContactsParams(
        users = this@reverseMapTo.usersList.map { it.reverseMapTo() },
        toAddUserComponentVisibility = this@reverseMapTo.toAddUserComponentVisibility,
        toAddGroupComponentVisibility = this@reverseMapTo.toAddGroupComponentVisibility,
    )
}