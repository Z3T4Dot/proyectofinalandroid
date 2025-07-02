package com.example.libros.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libros.data.local.dao.UserDao
import com.example.libros.data.local.entities.UserEntity

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            val user = UserEntity(email = email, password = password)
            userDao.insertUser(user)
            _loginSuccess.value = true
        }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = userDao.login(email, password)
            _loginSuccess.value = user != null
            if (user == null) {
                _errorMessage.value = null
            }
            onResult(user != null)
        }
    }

    fun setError(message: String) {
        _errorMessage.value = message
    }

    fun resetStatus() {
        _loginSuccess.value = false
        _errorMessage.value = null
    }

}
