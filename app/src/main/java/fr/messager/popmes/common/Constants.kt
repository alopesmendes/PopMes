package fr.messager.popmes.common

object Constants {
//    val gson: Gson = GsonBuilder()
//        .registerTypeAdapter(Instant::class.java, DateDeserializer())
//        .setPrettyPrinting()
//        .serializeNulls()
//        .create()

    // PARAMS
    const val PARAM_CONVERSATION = "conversation"
    const val PARAM_CONTACTS = "contacts"
    const val PARAM_TASKS = "tasks"

    // TABLES
    const val TABLE_CONTACTS = "users"

    // FIELDS
    const val FIELD_ID = "id"
    const val FIELD_GUID = "guid"
    const val FIELD_DATE_TIME = "date_time"
    const val FIELD_DATA = "data"
}