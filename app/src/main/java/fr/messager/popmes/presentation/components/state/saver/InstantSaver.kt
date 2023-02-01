package fr.messager.popmes.presentation.components.state.saver

import androidx.compose.runtime.saveable.listSaver
import java.time.Instant

val InstantSaverNullable = listSaver<Instant?, Any>(
    save = { it?.let { listOf(it.toEpochMilli()) } ?: listOf() },
    restore = { if (it.isEmpty()) null else Instant.ofEpochMilli(it[0] as Long) },
)

val InstantSaver = listSaver<Instant, Any>(
    save = { listOf(it.toEpochMilli()) },
    restore = { Instant.ofEpochMilli(it[0] as Long) },
)