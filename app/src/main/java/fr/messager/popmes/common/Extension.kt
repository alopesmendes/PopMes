package fr.messager.popmes.common

import fr.messager.popmes.common.Constants.gson
import fr.messager.popmes.presentation.navigation.arguments.NavData

object Extension {
    inline fun <reified V: NavData> V.mapNavDataToJson(): String {
        return gson.toJson(this)
    }

    inline fun <reified V: NavData> String?.mapToNavData(): V? {
        if (this.isNullOrBlank())
            return null
        return gson.fromJson(this, V::class.java)
    }
}