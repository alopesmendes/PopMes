package fr.messager.popmes.data.database.converters

import androidx.room.TypeConverter
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.mapper.ContactToContactProto.mapTo
import fr.messager.popmes.mapper.MessageToMessageProto.reverseMapTo
import fr.messager.popmes.mapper.TaskToTaskProto.reverseMapTo
import fr.messager.popmes.proto.ContactProto
import fr.messager.popmes.proto.MessageProto
import fr.messager.popmes.proto.TaskProto

class ProtoDataConverter {

    @TypeConverter
    fun fromByteArrayToContact(value: ByteArray?): Contact? {
        val proto = ContactProto.parseFrom(value)
        return proto?.mapTo()
    }

    @TypeConverter
    fun fromContactToByteArray(value: Contact?): ByteArray? {
        return value?.toByteArray()
    }

    @TypeConverter
    fun fromByteArrayToMessage(value: ByteArray?): Message? {
        val proto = MessageProto.parseFrom(value)
        return proto?.reverseMapTo()
    }

    @TypeConverter
    fun fromMessageToByteArray(value: Message?): ByteArray? {
        return value?.toByteArray()
    }

    @TypeConverter
    fun fromByteArrayToTask(value: ByteArray?): Task? {
        val proto = TaskProto.parseFrom(value)
        return proto?.reverseMapTo()
    }

    @TypeConverter
    fun fromMessageToByteArray(value: Task?): ByteArray? {
        return value?.toByteArray()
    }
}