package com.codingtroops.foodies.model.data

import com.codingtroops.foodies.database.AppDatabase
import com.codingtroops.foodies.database.asDomainModel
import com.codingtroops.foodies.model.response.User
import com.codingtroops.foodies.model.response.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoseLocalSource @Inject constructor(
    private val appDatabase: AppDatabase
) {
    val users: Flow<User> =
        appDatabase.usersDao.getUser()?.map { it.asDomainModel() }!!

    fun insertUser(user: User) {
        try {
            appDatabase.usersDao.insertUsers(user.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}