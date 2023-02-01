package fr.messager.popmes.domain.use_case.contact

import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.repository.ContactRepository
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val contactsRepository: ContactRepository,
) {
    suspend operator fun invoke(
        onContactsChange: suspend (List<Contact>) -> Unit
    ) {
        contactsRepository.collectContacts(onContactsChange)
    }
}