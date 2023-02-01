package fr.messager.popmes.data.repository

import fr.messager.popmes.data.database.dao.TaskDao
import fr.messager.popmes.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {
}