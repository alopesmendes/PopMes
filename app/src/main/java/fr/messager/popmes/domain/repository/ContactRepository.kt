package fr.messager.popmes.domain.repository

import fr.messager.popmes.domain.model.contact.Contact

interface ContactRepository {
    /**
     * Delete from guid
     *
     * @param guid
     */
    suspend fun deleteFromGuid(guid: String)

    /**
     * Insert contact
     *
     * @param contact
     */
    suspend fun insertContact(contact: Contact)

    /**
     * Collect contacts
     *
     * @param onContactsChange
     * @receiver
     */
    suspend fun collectContacts(
        onContactsChange: suspend (List<Contact>) -> Unit,
    )
}