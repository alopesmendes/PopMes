package fr.messager.popmes.data.repository

import android.util.Log
import androidx.room.Transaction
import fr.messager.popmes.data.database.dao.ContactDao
import fr.messager.popmes.data.database.dao.MessageDao
import fr.messager.popmes.data.database.entities.ContactsEntity
import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.domain.repository.ContactRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val messageDao: MessageDao,
) : ContactRepository {
    override suspend fun deleteFromGuid(guid: String) {
        contactDao.deleteFromGuid(guid)
    }

    @Transaction
    override suspend fun insertOrUpdateContact(contact: Contact) {
        val contactsEntity = contactDao.findContactEntityFromGuid(contact.id)
        if (contactsEntity == null) {
            Log.d(TAG, "insertOrUpdateContact: insert ${contact.id}")
            contactDao.insert(
                ContactsEntity(
                    guid = contact.id,
                    data = contact,
                )
            )
        } else {
            Log.d(TAG, "insertOrUpdateContact: update")
            contactDao.update(
                contactsEntity.copy(
                    data = contact
                )
            )

            messageDao
                .findMessagesByReferenceId(contact.id)
                .map { values -> values.filterNotNull() }
                .map { values -> values.filter { it.data?.from?.id == contact.id } }
                .collect { values ->
                    messageDao.update(
                        *values
                        .map { value ->
                            value.copy(
                                data = value.data?.copy(
                                    from = contact as User,
                                    destination = contact,
                                )
                            )
                        }
                        .toTypedArray()
                    )
                }
        }
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