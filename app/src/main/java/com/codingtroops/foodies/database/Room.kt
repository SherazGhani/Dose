package com.codingtroops.foodies.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Query("select * from UserEntity")
    fun getUser(): Flow<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: UserEntity)
}

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}