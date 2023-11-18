package com.example.hobbyapp.HomeActivity

import androidx.lifecycle.*
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.UserDatabase.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {

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
    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    class HomeViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}