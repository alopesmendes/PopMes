package fr.messager.popmes.domain.model.contact

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    override val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
): Contact, Parcelable {
    override fun fullName(): String = "$firstName $lastName"
}
