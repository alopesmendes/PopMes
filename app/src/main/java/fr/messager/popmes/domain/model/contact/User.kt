package fr.messager.popmes.domain.model.contact

data class User(
    override val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
): Contact {
    override fun fullName(): String = "$firstName $lastName"
}
