package fr.messager.popmes.data.database.entities

import fr.messager.popmes.domain.model.ProtoData
import java.time.Instant

interface BaseEntity<T: ProtoData> {
    val id: Long
    val guid: String
    val dateTime: Instant
    val data: T?
}