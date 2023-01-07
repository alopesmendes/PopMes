package fr.messager.popmes.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.Instant

object Constants {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, DateDeserializer())
        .setPrettyPrinting()
        .serializeNulls()
        .create()

    const val PARAM_GROUP_CHAT = "group_chat"
}