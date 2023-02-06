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
import fr.messager.popmes.domain.model.contact.Group
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.use_case.contact.DeleteContactUseCase
import fr.messager.popmes.domain.use_case.contact.GetContactsUseCase
import fr.messager.popmes.domain.use_case.contact.InsertContactUseCase
import fr.messager.popmes.mapper.ContactParamsToContactParamsProto.reverseMapTo
import fr.messager.popmes.proto.ContactsParamsProto
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertContactUseCase: InsertContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val getContactsUseCase: GetContactsUseCase,
) : ViewModel() {
    private val contactsParams =
        savedStateHandle.get<String>(Constants.PARAM_CONTACTS).mapToNavData(
            parseFrom = ContactsParamsProto::parseFrom,
            mapTo = { this.reverseMapTo() }
        )

    var selectContact: Contact? by mutableStateOf(null)

    private val _contacts = mutableStateListOf<User>()
    val contacts = _contacts

    private val _contactsAddedToGroup = mutableStateListOf<User>()
    val contactsAddedToGroup: List<User> = _contactsAddedToGroup

    var toAddUserComponentVisibility by mutableStateOf(false)
    var toAddGroupComponentVisibility by mutableStateOf(false)

    init {
        initializeContacts()
        initComponentsVisibility()
    }

    private fun initializeContacts() {
        viewModelScope.launch {
            getContactsUseCase(
                onContactsChange = {
                    _contacts.clear()
                    _contacts.addAll(it.filterIsInstance<User>())
                }
            )
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
            insertContactUseCase(contact)
        }
    }

    fun deleteContact(guid: String) {
        viewModelScope.launch {
            deleteContactUseCase(guid)
        }
    }

    fun toEditContact(contact: Contact) {
        viewModelScope.launch {
            when (contact) {
                is User -> { toAddUserComponentVisibility = true }
                is Group -> { toAddGroupComponentVisibility = true }
            }
        }
    }
}