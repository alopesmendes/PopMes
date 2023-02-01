package fr.messager.popmes.domain.use_case.contact

import fr.messager.popmes.domain.repository.ContactRepository
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(
        guid: String,
    ) {
        contactRepository.deleteFromGuid(guid)
    }
}