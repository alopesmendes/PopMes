package fr.messager.popmes.presentation.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.messager.popmes.common.Constants
import fr.messager.popmes.common.Extension.mapToNavData
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.task.Task
import fr.messager.popmes.mapper.TaskParamsToTaskParamsProto.reverseMapTo
import fr.messager.popmes.proto.TaskParamsProto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val taskArguments = savedStateHandle.get<String>(Constants.PARAM_TASKS).mapToNavData(
        parseFrom = TaskParamsProto::parseFrom,
        mapTo = { this.reverseMapTo() },
    )

    private val _contactsAdded = mutableStateListOf<Contact>()
    val contactsAdded: List<Contact> = _contactsAdded

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks
    var toAddScheduleVisibility by mutableStateOf(false)
    var toAddTaskVisibility by mutableStateOf(false)

    init {
        initializeParamsFields()
    }

    private fun initializeParamsFields() {
        viewModelScope.launch {
            toAddTaskVisibility = taskArguments?.toAddTaskVisibility == true
            toAddScheduleVisibility = taskArguments?.toAddScheduleVisibility == true
        }
    }

    fun addContact(user: Contact) {
        viewModelScope.launch {
            _contactsAdded.add(user)
        }
    }

    fun removeContact(index: Int) {
        viewModelScope.launch {
            require(index in _contactsAdded.indices)
            _contactsAdded.removeAt(index)
        }
    }

    fun onAddTask(
        task: Task
    ) {
        viewModelScope.launch {
            _tasks.add(task)
        }
    }


}