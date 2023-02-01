package fr.messager.popmes.domain.model.task

import fr.messager.popmes.domain.model.ProtoData
import fr.messager.popmes.mapper.TaskToTaskProto.mapTo
import java.time.Instant

data class Task(
    val id: String,
    val title: String,
    val priority: TaskPriority,
    val type: TaskType?,
    val beginDate: Instant,
    val endDate: Instant?,
    val description: String,
): ProtoData {
    override fun toProto() = this.mapTo()
}
