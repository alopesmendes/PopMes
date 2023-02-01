package fr.messager.popmes.domain.model.task

sealed class TaskType {
    object Schedule: TaskType()

    object Task: TaskType()
}
