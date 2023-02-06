package fr.messager.popmes.domain.use_case.contact

import fr.messager.popmes.domain.model.contact.Contact
import fr.messager.popmes.domain.repository.ContactRepository
import javax.inject.Inject

class InsertContactUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(
        contact: Contact
    ) {
        contactRepository.insertOrUpdateContact(contact)
    }
}