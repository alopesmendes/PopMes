package fr.messager.popmes.common

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateDeserializer : JsonDeserializer<Instant?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        element: JsonElement,
        arg1: Type,
        arg2: JsonDeserializationContext
    ): Instant? {
        val dateString = element.asString
        return try {
            val instant = Instant.parse(dateString)
            instant
        } catch (exp: DateTimeParseException) {
            try {
                val localDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            } catch (e: Exception) {
                null
            }
        }
    }
}