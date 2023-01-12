package fr.messager.popmes.mapper

import fr.messager.popmes.domain.model.contact.User
import fr.messager.popmes.proto.UserProto
import fr.messager.popmes.proto.userProto

object UserToUserProto: ReverseMapper<User, UserProto> {
    override fun User.mapTo(): UserProto = userProto {
        id = this@mapTo.id
        firstName = this@mapTo.firstName
        lastName = this@mapTo.lastName
        phoneNumber = this@mapTo.phoneNumber
    }

    override fun UserProto.reverseMapTo(): User = User(
        id = this@reverseMapTo.id,
        firstName = this@reverseMapTo.firstName,
        lastName = this@reverseMapTo.lastName,
        phoneNumber = this@reverseMapTo.phoneNumber,
    )
}