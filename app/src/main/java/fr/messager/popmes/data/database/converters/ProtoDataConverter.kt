package fr.messager.popmes.data.database.converters

import androidx.room.TypeConverter
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.mapper.ContactToContactProto.mapTo
import fr.messager.popmes.proto.ContactProto

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
}