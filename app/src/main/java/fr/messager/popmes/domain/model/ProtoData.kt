package fr.messager.popmes.domain.model

import com.google.protobuf.MessageLite

interface ProtoData {
    fun toProto(): MessageLite? = null

    fun toByteArray(): ByteArray? = toProto()?.toByteArray()
}