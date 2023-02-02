package fr.messager.popmes.mapper

import com.google.protobuf.Timestamp
import com.google.protobuf.timestamp
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.model.message.Message
import fr.messager.popmes.domain.model.message.MessageType
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.domain.model.task.TaskPriority
import fr.messager.popmes.domain.model.task.TaskType
import fr.messager.popmes.mapper.CameraToCameraProto.mapTo
import fr.messager.popmes.mapper.CameraToCameraProto.reverseMapTo
import fr.messager.popmes.mapper.ContactToContactProto.mapTo
import fr.messager.popmes.mapper.ContactToContactProto.reverseMapTo
import fr.messager.popmes.mapper.FileToFileProto.mapTo
import fr.messager.popmes.mapper.FileToFileProto.reverseMapTo
import fr.messager.popmes.mapper.GifToGifProto.mapTo
import fr.messager.popmes.mapper.GifToGifProto.reverseMapTo
import fr.messager.popmes.mapper.GroupToGroupProto.mapTo
import fr.messager.popmes.mapper.GroupToGroupProto.reverseMapTo
import fr.messager.popmes.mapper.InstantToTimestamp.mapTo
import fr.messager.popmes.mapper.InstantToTimestamp.reverseMapTo
import fr.messager.popmes.mapper.MessageDataToMessageDataProto.mapTo
import fr.messager.popmes.mapper.MessageDataToMessageDataProto.reverseMapTo
import fr.messager.popmes.mapper.MessageToMessageProto.mapTo
import fr.messager.popmes.mapper.MessageToMessageProto.reverseMapTo
import fr.messager.popmes.mapper.MessageTypeToMessageTypeProto.mapTo
import fr.messager.popmes.mapper.MessageTypeToMessageTypeProto.reverseMapTo
import fr.messager.popmes.mapper.PhotoToPhotoProto.mapTo
import fr.messager.popmes.mapper.PhotoToPhotoProto.reverseMapTo
import fr.messager.popmes.mapper.SurveyToSurveyProto.mapTo
import fr.messager.popmes.mapper.SurveyToSurveyProto.reverseMapTo
import fr.messager.popmes.mapper.TaskPriorityToTaskPriority.mapTo
import fr.messager.popmes.mapper.TaskPriorityToTaskPriority.reverseMapTo
import fr.messager.popmes.mapper.TaskTypeToTaskTypeProto.mapTo
import fr.messager.popmes.mapper.TaskTypeToTaskTypeProto.reverseMapTo
import fr.messager.popmes.mapper.UserToUserProto.mapTo
import fr.messager.popmes.mapper.UserToUserProto.reverseMapTo
import fr.messager.popmes.mapper.VocalToVocalProto.mapTo
import fr.messager.popmes.mapper.VocalToVocalProto.reverseMapTo
import fr.messager.popmes.presentation.navigation.arguments.ContactsParams
import fr.messager.popmes.presentation.navigation.arguments.ConversationParams
import fr.messager.popmes.presentation.navigation.arguments.TaskParams
import fr.messager.popmes.proto.CameraProto
import fr.messager.popmes.proto.ContactProto
import fr.messager.popmes.proto.ContactsParamsProto
import fr.messager.popmes.proto.ConversationParamsProto
import fr.messager.popmes.proto.FileProto
import fr.messager.popmes.proto.GifProto
import fr.messager.popmes.proto.GroupProto
import fr.messager.popmes.proto.MessageDataProto
import fr.messager.popmes.proto.MessageProto
import fr.messager.popmes.proto.MessageTypeProto
import fr.messager.popmes.proto.PhotoProto
import fr.messager.popmes.proto.SurveyProto
import fr.messager.popmes.proto.TaskParamsProto
import fr.messager.popmes.proto.TaskPriorityProto
import fr.messager.popmes.proto.TaskProto
import fr.messager.popmes.proto.TaskTypeProto
import fr.messager.popmes.proto.UserProto
import fr.messager.popmes.proto.VocalProto
import fr.messager.popmes.proto.cameraProto
import fr.messager.popmes.proto.contactProto
import fr.messager.popmes.proto.contactsParamsProto
import fr.messager.popmes.proto.conversationParamsProto
import fr.messager.popmes.proto.fileProto
import fr.messager.popmes.proto.gifProto
import fr.messager.popmes.proto.groupProto
import fr.messager.popmes.proto.messageDataProto
import fr.messager.popmes.proto.messageProto
import fr.messager.popmes.proto.messageTypeProto
import fr.messager.popmes.proto.photoProto
import fr.messager.popmes.proto.scheduleProto
import fr.messager.popmes.proto.surveyProto
import fr.messager.popmes.proto.taskEventProto
import fr.messager.popmes.proto.taskParamsProto
import fr.messager.popmes.proto.taskProto
import fr.messager.popmes.proto.taskTypeProto
import fr.messager.popmes.proto.userProto
import fr.messager.popmes.proto.vocalProto
import java.time.Instant

object UserToUserProto: ReverseMapper<User, UserProto> {
    override fun User.mapTo(): UserProto = userProto {
        id = this@mapTo.id
        firstName = this@mapTo.firstName
        lastName = this@mapTo.lastName
        phoneNumber = this@mapTo.phoneNumber
    }

    override fun UserProto.reverseMapTo(): User = User(
        id = this@reverseMapTo.id,
        firstName = this@reverseMapTo.firstName,
        lastName = this@reverseMapTo.lastName,
        phoneNumber = this@reverseMapTo.phoneNumber,
    )
}

object GroupToGroupProto: ReverseMapper<Group, GroupProto> {
    override fun Group.mapTo(): GroupProto = groupProto {
        id = this@mapTo.id
        users.addAll(this@mapTo.users.map { it.mapTo() })
        name = this@mapTo.name
    }

    override fun GroupProto.reverseMapTo(): Group = Group(
        id = this@reverseMapTo.id,
        name = this@reverseMapTo.name,
        users = this@reverseMapTo.usersList.map { it.reverseMapTo() },
    )
}



object ContactToContactProto: Mapper<ContactProto, Contact> {
    override fun ContactProto.mapTo(): Contact {
        return when(this@mapTo.innerMessageCase) {
            ContactProto.InnerMessageCase.USER -> this@mapTo.user.reverseMapTo()
            ContactProto.InnerMessageCase.GROUP -> this@mapTo.group.reverseMapTo()

            ContactProto.InnerMessageCase.INNERMESSAGE_NOT_SET -> throw IllegalArgumentException("contact proto not set")
            else -> throw IllegalArgumentException("contact proto undefined")
        }
    }
    fun <T: Contact> T.reverseMapTo(): ContactProto = contactProto {
        when (this@reverseMapTo) {
            is User -> { user = this@reverseMapTo.mapTo() }
            is Group -> { group = this@reverseMapTo.mapTo() }
        }
    }
}

object MessageDataToMessageDataProto: ReverseMapper<MessageType.MessageData, MessageDataProto> {
    override fun MessageType.MessageData.mapTo(): MessageDataProto = messageDataProto {
        text = this@mapTo.text
    }
    override fun MessageDataProto.reverseMapTo(): MessageType.MessageData = MessageType.MessageData(
        text = this@reverseMapTo.text
    )
}

object FileToFileProto: ReverseMapper<MessageType.File, FileProto> {
    override fun MessageType.File.mapTo(): FileProto = fileProto {

    }

    override fun FileProto.reverseMapTo(): MessageType.File = MessageType.File
}

object CameraToCameraProto: ReverseMapper<MessageType.Camera, CameraProto> {
    override fun MessageType.Camera.mapTo(): CameraProto = cameraProto {  }

    override fun CameraProto.reverseMapTo(): MessageType.Camera = MessageType.Camera
}

object GifToGifProto: ReverseMapper<MessageType.GIF, GifProto> {
    override fun MessageType.GIF.mapTo(): GifProto = gifProto {  }

    override fun GifProto.reverseMapTo(): MessageType.GIF = MessageType.GIF
}

object PhotoToPhotoProto: ReverseMapper<MessageType.Photo, PhotoProto> {
    override fun MessageType.Photo.mapTo(): PhotoProto = photoProto {  }

    override fun PhotoProto.reverseMapTo(): MessageType.Photo = MessageType.Photo
}

object SurveyToSurveyProto: ReverseMapper<MessageType.Survey, SurveyProto> {
    override fun MessageType.Survey.mapTo(): SurveyProto = surveyProto {  }

    override fun SurveyProto.reverseMapTo(): MessageType.Survey = MessageType.Survey
}

object VocalToVocalProto: ReverseMapper<MessageType.Vocal, VocalProto> {
    override fun MessageType.Vocal.mapTo(): VocalProto = vocalProto {  }

    override fun VocalProto.reverseMapTo(): MessageType.Vocal = MessageType.Vocal
}

object MessageTypeToMessageTypeProto: ReverseMapper<MessageTypeProto, MessageType?> {
    override fun MessageTypeProto.mapTo(): MessageType? {
        return when (this@mapTo.innerMessageCase) {
            MessageTypeProto.InnerMessageCase.MESSAGE_DATA -> this@mapTo.messageData.reverseMapTo()
            MessageTypeProto.InnerMessageCase.FILE -> this@mapTo.file.reverseMapTo()
            MessageTypeProto.InnerMessageCase.CAMERA -> this@mapTo.camera.reverseMapTo()
            MessageTypeProto.InnerMessageCase.GIF -> this@mapTo.gif.reverseMapTo()
            MessageTypeProto.InnerMessageCase.PHOTO -> this@mapTo.photo.reverseMapTo()
            MessageTypeProto.InnerMessageCase.SURVEY -> this@mapTo.survey.reverseMapTo()
            MessageTypeProto.InnerMessageCase.VOCAL -> this@mapTo.vocal.reverseMapTo()

            MessageTypeProto.InnerMessageCase.INNERMESSAGE_NOT_SET -> null
            else -> null
        }
    }

    override fun MessageType?.reverseMapTo(): MessageTypeProto = messageTypeProto {
        when (this@reverseMapTo) {
            is MessageType.MessageData -> { messageData = this@reverseMapTo.mapTo() }
            is MessageType.Vocal -> { vocal = this@reverseMapTo.mapTo() }
            is MessageType.GIF -> { gif = this@reverseMapTo.mapTo() }
            is MessageType.Survey -> { survey = this@reverseMapTo.mapTo() }
            is MessageType.Photo -> { photo = this@reverseMapTo.mapTo() }
            is MessageType.Camera -> { camera = this@reverseMapTo.mapTo() }
            is MessageType.File -> { file = this@reverseMapTo.mapTo() }
            else -> { }
        }
    }
}


object InstantToTimestamp: ReverseMapper<Instant?, Timestamp> {
    override fun Instant?.mapTo(): Timestamp = timestamp {
        if (this@mapTo != null) {
            nanos = this@mapTo.nano
            seconds = this@mapTo.epochSecond
        }
    }

    override fun Timestamp.reverseMapTo(): Instant? {
        return if (this@reverseMapTo.serializedSize != 0)
            Instant.ofEpochSecond(
                this@reverseMapTo.seconds,
                this@reverseMapTo.nanos.toLong()
            )
        else
            null
    }
}

object MessageToMessageProto: ReverseMapper<Message, MessageProto> {
    override fun Message.mapTo(): MessageProto = messageProto {
        id = this@mapTo.id
        type = this@mapTo.messageType.reverseMapTo()
        date = this@mapTo.date.mapTo()
        from = this@mapTo.from.mapTo()
        to = this@mapTo.to.reverseMapTo()
    }

    override fun MessageProto.reverseMapTo(): Message = Message(
        id = this@reverseMapTo.id,
        messageType = this@reverseMapTo.type.mapTo(),
        date = this@reverseMapTo.date.reverseMapTo() ?: Instant.now(),
        to = this@reverseMapTo.to.mapTo(),
        from = this@reverseMapTo.from.reverseMapTo(),
    )
}

object TaskTypeToTaskTypeProto: ReverseMapper<TaskType?, TaskTypeProto> {
    override fun TaskType?.mapTo(): TaskTypeProto = taskTypeProto {
        when (this@mapTo) {
            is TaskType.Task -> {
                task = taskEventProto {  }
            }
            is TaskType.Schedule -> {
                schedule = scheduleProto { }
            }
            else -> {}
        }
    }

    override fun TaskTypeProto.reverseMapTo(): TaskType? {
        return when(this@reverseMapTo.innerMessageCase) {
            TaskTypeProto.InnerMessageCase.TASK -> TaskType.Task

            TaskTypeProto.InnerMessageCase.SCHEDULE -> TaskType.Schedule

            TaskTypeProto.InnerMessageCase.INNERMESSAGE_NOT_SET -> null
            else -> null
        }
    }
}

object TaskPriorityToTaskPriority: ReverseMapper<TaskPriority, TaskPriorityProto> {
    override fun TaskPriority.mapTo(): TaskPriorityProto {
        return when (this@mapTo) {
            TaskPriority.LOW -> TaskPriorityProto.LOW
            TaskPriority.HIGH -> TaskPriorityProto.HIGH
            TaskPriority.URGENT -> TaskPriorityProto.URGENT
        }
    }

    override fun TaskPriorityProto.reverseMapTo(): TaskPriority {
        return when (this@reverseMapTo) {
            TaskPriorityProto.LOW -> TaskPriority.LOW
            TaskPriorityProto.HIGH -> TaskPriority.HIGH
            TaskPriorityProto.URGENT -> TaskPriority.URGENT
            TaskPriorityProto.UNRECOGNIZED -> TaskPriority.LOW
        }
    }
}

object TaskToTaskProto: ReverseMapper<Task, TaskProto> {
    override fun Task.mapTo(): TaskProto = taskProto {
        id = this@mapTo.id
        title = this@mapTo.title
        priority = this@mapTo.priority.mapTo()
        type = this@mapTo.type.mapTo()
        beginDate = this@mapTo.beginDate.mapTo()
        endDate = this@mapTo.endDate.mapTo()
        description = this@mapTo.description
    }
    override fun TaskProto.reverseMapTo(): Task = Task(
        id = this@reverseMapTo.id,
        title = this@reverseMapTo.title,
        priority = this@reverseMapTo.priority.reverseMapTo(),
        type = this@reverseMapTo.type.reverseMapTo(),
        beginDate = this@reverseMapTo.beginDate.reverseMapTo() ?: Instant.now(),
        endDate = this@reverseMapTo.endDate.reverseMapTo(),
        description = this@reverseMapTo.description,
    )
}

object ConversationParamsToConversationParamsProto: ReverseMapper<ConversationParams, ConversationParamsProto> {
    override fun ConversationParams.mapTo(): ConversationParamsProto = conversationParamsProto {
        messages.addAll(this@mapTo.messages.map { it.mapTo() })
        if (this@mapTo.contact != null)
            contact = this@mapTo.contact.reverseMapTo()
    }

    override fun ConversationParamsProto.reverseMapTo(): ConversationParams = ConversationParams(
        messages = this@reverseMapTo.messagesList.map { it.reverseMapTo() },
        contact = this@reverseMapTo.contact.mapTo(),
    )
}

object ContactParamsToContactParamsProto: ReverseMapper<ContactsParams, ContactsParamsProto> {
    override fun ContactsParams.mapTo(): ContactsParamsProto = contactsParamsProto {
        users.addAll(this@mapTo.users.map { it.mapTo() })
        toAddUserComponentVisibility = this@mapTo.toAddUserComponentVisibility
        toAddGroupComponentVisibility = this@mapTo.toAddGroupComponentVisibility
    }

    override fun ContactsParamsProto.reverseMapTo(): ContactsParams = ContactsParams(
        users = this@reverseMapTo.usersList.map { it.reverseMapTo() },
        toAddUserComponentVisibility = this@reverseMapTo.toAddUserComponentVisibility,
        toAddGroupComponentVisibility = this@reverseMapTo.toAddGroupComponentVisibility,
    )
}

object TaskParamsToTaskParamsProto: ReverseMapper<TaskParams, TaskParamsProto> {
    override fun TaskParams.mapTo(): TaskParamsProto = taskParamsProto {
        toAddTaskVisibility = this@mapTo.toAddTaskVisibility
        toAddScheduleVisibility = this@mapTo.toAddScheduleVisibility
        users.addAll(this@mapTo.contacts.map { it.mapTo() })
    }

    override fun TaskParamsProto.reverseMapTo(): TaskParams = TaskParams(
        contacts = this@reverseMapTo.usersList.map { it.reverseMapTo() },
        toAddTaskVisibility = this@reverseMapTo.toAddTaskVisibility,
        toAddScheduleVisibility = this@reverseMapTo.toAddScheduleVisibility,
    )
}