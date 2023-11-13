package com.example.hobbyapp.LoginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.UserDatabase.UserRepository

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    val allUsers: LiveData<Map<Int, User>> = repository.allUsers.asLiveData()


    fun checkUserExistsByEmailAndPassword(email: String, password:String): Pair<Boolean, Int?> {
        val user = repository.checkUserByEmailAndPassword(email,password)
        val userExists = user != null
        val userId = user?.id // Assuming 'id' is the property representing the user's ID
        return Pair(userExists, userId)
    }
    class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}