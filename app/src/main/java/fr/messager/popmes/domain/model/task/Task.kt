package fr.messager.popmes.domain.model.task

import java.time.Instant

data class Task(
    val id: String,
    val priority: TaskPriority,
    val type: TaskType,
    val beginDate: Instant,
    val endDate: Instant,
)
