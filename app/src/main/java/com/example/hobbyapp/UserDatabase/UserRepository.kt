package com.example.hobbyapp.UserDatabase

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    //sets up database
    val allUsers: Flow<Map<Int,User>> = userDao.getUsers()

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User){
        userDao.insert(user)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun getUser(userId: Int): User {
        return userDao.getUser(userId)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }

    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    @Suppress("RedudndantSuspendModifier")
    @WorkerThread
    suspend fun checkUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}