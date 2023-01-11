package fr.messager.popmes.domain.model.task

sealed class TaskType {
    object Meetup: TaskType()

    object Alarm: TaskType()
}
