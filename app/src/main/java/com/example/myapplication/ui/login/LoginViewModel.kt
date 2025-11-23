package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun login(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val email = _uiState.value.email
            val password = _uiState.value.password
            
            // Simulate async operation or use actual repository suspend function
            // For now using the existing singleton logic, but ideally this calls a suspend function
            if (userRepository.validateLogin(email, password)) {
                onSuccess(email)
            } else {
                _uiState.value = _uiState.value.copy(errorMessage = "Correo o contraseña incorrectos")
            }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
