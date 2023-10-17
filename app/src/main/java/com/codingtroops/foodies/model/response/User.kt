package com.codingtroops.foodies.model.response

import com.codingtroops.foodies.database.UserEntity

data class User(
    var id: Int = 0,
    var email: String = "",
    var password: String = ""
)

fun User.asDatabaseModel(): UserEntity {
    return UserEntity(
            id = this.id,
            email = this.email,
            password = this.password
        )
}