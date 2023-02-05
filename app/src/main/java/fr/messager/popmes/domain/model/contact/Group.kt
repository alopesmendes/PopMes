package fr.messager.popmes.domain.model.contact

data class Group(
    override val id: String,
    val name: String,
    val users: List<User>,
    override val description: String,
): Contact {
    override fun fullName(): String = name
    override fun shortName(): String = name
}
