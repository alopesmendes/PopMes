package fr.messager.popmes.domain.model.contact

import fr.messager.popmes.domain.model.ProtoData
import fr.messager.popmes.mapper.ContactToContactProto.reverseMapTo
import fr.messager.popmes.proto.ContactProto

interface Contact: ProtoData {
    val id: String
    fun fullName(): String
    override fun toProto(): ContactProto = this.reverseMapTo()
}