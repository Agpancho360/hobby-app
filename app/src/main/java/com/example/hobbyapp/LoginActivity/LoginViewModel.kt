package com.example.hobbyapp.LoginActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.UserDatabase.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    val allUsers: LiveData<Map<Int, User>> = repository.allUsers.asLiveData()

    // LiveData to observe login results
    private val login = MutableLiveData<User?>()
    val loginResult: LiveData<User?> = login


    fun checkUserExistsByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.checkUserByEmailAndPassword(email, password)
            login.postValue(user)
        }
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