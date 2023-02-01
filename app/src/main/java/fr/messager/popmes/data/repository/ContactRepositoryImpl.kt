package fr.messager.popmes.data.repository

import android.util.Log
import fr.messager.popmes.data.database.dao.ContactDao
import fr.messager.popmes.data.database.entities.ContactsEntity
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.repository.ContactRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
): ContactRepository {
    override suspend fun deleteFromGuid(guid: String) {
        contactDao.deleteFromId(guid)
    }

    override suspend fun insertContact(contact: Contact) {
        contactDao.insert(
            ContactsEntity(
                guid = contact.id,
                data = contact,
            )
        )
    }

    companion object {
        private val TAG = ContactRepository::class.simpleName
    }

    override suspend fun collectContacts(onContactsChange: suspend (List<Contact>) -> Unit) {
        contactDao
            .findContacts()
            .map { value -> value.mapNotNull { it.data } }
            .collect {
                Log.d(TAG, "collectContacts: ${it.size}")
                onContactsChange(it)
            }
    }
}