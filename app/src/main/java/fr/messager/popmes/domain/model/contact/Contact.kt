package fr.messager.popmes.domain.model.contact

interface Contact {
    val id: String

    fun fullName(): String
}