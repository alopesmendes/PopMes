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
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.mapper.ContactParamsToContactParamsProto.reverseMapTo
import fr.messager.popmes.proto.ContactsParamsProto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val contactsParams =
        savedStateHandle.get<String>(Constants.PARAM_CONTACTS).mapToNavData(
            parseFrom = ContactsParamsProto::parseFrom,
            mapTo = { this.reverseMapTo() }
        )

    private val _contactsAddedToGroup = mutableStateListOf<User>()
    val contactsAddedToGroup: List<User> = _contactsAddedToGroup

    private val _contactsGroup = mutableStateListOf<User>()
    val contactsGroup: List<User> = _contactsGroup

    var toAddUserComponentVisibility by mutableStateOf(false)
    var toAddGroupComponentVisibility by mutableStateOf(false)

    init {
        initializeContactGroup()
        initComponentsVisibility()
    }

    private fun initializeContactGroup() {
        viewModelScope.launch {
            _contactsGroup.clear()
            _contactsGroup.addAll(contactsParams?.users ?: listOf())
        }
    }

    private fun initComponentsVisibility() {
        viewModelScope.launch {
            toAddUserComponentVisibility = contactsParams?.toAddUserComponentVisibility == true
            toAddGroupComponentVisibility = contactsParams?.toAddGroupComponentVisibility == true
        }
    }

    fun addContactToGroup(
        user: User,
    ) {
        viewModelScope.launch {
            _contactsAddedToGroup.add(user)
        }
    }

    fun removeContactOfGroup(
        index: Int
    ) {
        viewModelScope.launch {
            require(index in _contactsAddedToGroup.indices)
            _contactsAddedToGroup.removeAt(index)
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {

        }
    }

}