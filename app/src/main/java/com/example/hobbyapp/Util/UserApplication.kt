package com.example.hobbyapp.Util

import android.app.Application
import com.example.hobbyapp.UserDatabase.UserRepository
import com.example.hobbyapp.UserDatabase.UserRoomDatabase
//import com.example.geocamera1.Util.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy{ UserRepository(database.userDao())}
}