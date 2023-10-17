package com.codingtroops.foodies.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingtroops.foodies.model.response.User

@Entity
data class UserEntity constructor(
    @PrimaryKey
    val id: Int,
    val email: String,
    val password: String
)

fun UserEntity.asDomainModel(): User {
    return User(
        id = this.id,
        email = this.email,
        password = this.password
    )
}