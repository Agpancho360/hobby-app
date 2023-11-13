package com.example.hobbyapp.CreateUserActivity

import androidx.lifecycle.*
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.UserDatabase.UserRepository
import kotlinx.coroutines.launch

class CreateUserViewModel(private val repository: UserRepository): ViewModel() {

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

    class CreateUserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(CreateUserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return CreateUserViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}