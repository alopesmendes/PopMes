package fr.messager.popmes.domain.model.contact

import fr.messager.popmes.common.EmptyData
import java.util.UUID

data class User(
    override val id: String = "${UUID.randomUUID()}",
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    override val description: String,
): Contact {
    override fun fullName(): String = "$firstName $lastName"
    override fun shortName(): String = firstName

    companion object: EmptyData<User> {
        override val unspecified: User = User(
            firstName = "",
            lastName = "",
            phoneNumber = "",
            description = "",
        )
    }
}
