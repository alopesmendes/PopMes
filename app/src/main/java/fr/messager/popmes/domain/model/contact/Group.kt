package fr.messager.popmes.domain.model.contact

import fr.messager.popmes.common.EmptyData
import java.util.UUID

data class Group(
    override val id: String = "${UUID.randomUUID()}",
    val name: String,
    val users: List<User>,
    override val description: String,
): Contact {
    override fun fullName(): String = name
    override fun shortName(): String = name

    companion object: EmptyData<Group> {
        override val unspecified: Group = Group(
            name = "",
            users = listOf(),
            description = "",
        )
    }
}
