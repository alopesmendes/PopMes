package fr.messager.popmes.presentation.navigation.arguments

import com.google.protobuf.MessageLite
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.mapper.TaskParamsToTaskParamsProto.mapTo

data class TaskParams(
    val contacts: List<User>,
    val toAddTaskVisibility: Boolean,
    val toAddScheduleVisibility: Boolean,
): NavData {
    override fun toProto(): MessageLite = this.mapTo()
}
