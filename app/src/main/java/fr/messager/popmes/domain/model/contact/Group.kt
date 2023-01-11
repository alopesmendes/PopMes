package fr.messager.popmes.domain.model.contact

data class Group(
    override val id: String,
    val name: String,
    val users: List<User>,
): Contact
