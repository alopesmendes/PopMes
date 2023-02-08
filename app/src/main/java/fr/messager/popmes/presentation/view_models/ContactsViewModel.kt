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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _selectOrCreateContact: MutableStateFlow<Contact> = MutableStateFlow(User.unspecified)
    val selectOrCreateContact: StateFlow<Contact> = _selectOrCreateContact.asStateFlow()

    private val _contacts = mutableStateListOf<User>()
    val contacts: List<User> = _contacts

    private val _contactsAddedToGroup = mutableStateListOf<User>()
    val contactsAddedToGroup: List<User> = _contactsAddedToGroup

    var toAddUserComponentVisibility by mutableStateOf(false)
    var toAddGroupComponentVisibility by mutableStateOf(false)

    init {
        initializeContacts()
        initComponentsVisibility()
        initSelectOrCreateContact()
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

    private fun initSelectOrCreateContact() {
        viewModelScope.launch {
            contactsParams?.selectOrCreateContact?.let {
                _selectOrCreateContact.update { it }
            }
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
                is User -> {
                    toAddUserComponentVisibility = true
                }

                is Group -> {
                    toAddGroupComponentVisibility = true
                }
            }
        }
    }

    fun onContactChange(contact: Contact) {
        viewModelScope.launch {
            _selectOrCreateContact.update { contact }
        }
    }
}