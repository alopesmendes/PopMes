package fr.messager.popmes.presentation.navigation.arguments

import com.google.protobuf.MessageLite
import fr.messager.popmes.common.Extension.toHex

interface NavData {
    fun toProto(): MessageLite

    fun toHex(): String = toProto().toByteArray().toHex()
}