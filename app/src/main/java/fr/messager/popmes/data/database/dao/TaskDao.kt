package fr.messager.popmes.data.database.dao

import androidx.room.Dao
import fr.messager.popmes.common.Constants
import fr.messager.popmes.data.database.entities.TaskEntity
import fr.messager.popmes.domain.model.task.Task

@Dao
abstract class TaskDao: BaseDao<Task, TaskEntity>(
    tableName = Constants.TABLE_TASKS
) {
}