package com.example.hobbyapp.UserDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    val allUsers: LiveData<List<User>> = repository.allUsers.asLiveData()
    // LiveData to observe login results
    private val login = MutableLiveData<User?>()
    val loginResult: LiveData<User?> = login
    val _user = MutableLiveData<User>().apply { value=null }
    val user:LiveData<User>
        get() = _user
    fun start(userId:Int){
        viewModelScope.launch {
            repository.allUsers.collect{
                _user.value = it[userId]
            }
        }
    }
    fun insert(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }
    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }
    fun checkUserExistsByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.checkUserByEmailAndPassword(email, password)
            login.postValue(user)
        }
    }

    class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(UserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}