package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.model.Pet
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.FullNameChanged -> _uiState.value = _uiState.value.copy(fullName = event.fullName)
            is RegisterUiEvent.EmailChanged -> _uiState.value = _uiState.value.copy(email = event.email, emailError = null)
            is RegisterUiEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.password)
            is RegisterUiEvent.ConfirmPasswordChanged -> _uiState.value = _uiState.value.copy(confirmPassword = event.confirmPassword)
            is RegisterUiEvent.PetNameChanged -> _uiState.value = _uiState.value.copy(petName = event.petName)
            is RegisterUiEvent.PetTypeChanged -> _uiState.value = _uiState.value.copy(petType = event.petType)
            is RegisterUiEvent.Submit -> register()
        }
    }

    private fun register() {
        val currentState = _uiState.value
        if (validate(currentState)) {
            viewModelScope.launch {
                val success = userRepository.registerUser(User(email = currentState.email, password = currentState.password))
                if (success) {
                    userRepository.addPet(Pet(name = currentState.petName, type = currentState.petType, ownerEmail = currentState.email))
                    _uiState.value = _uiState.value.copy(isRegistrationSuccess = true)
                } else {
                    _uiState.value = _uiState.value.copy(emailError = "El correo ya está en uso")
                }
            }
        }
    }

    private fun validate(state: RegisterUiState): Boolean {
        // Simple validation logic
        val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        val isPasswordValid = state.password.length >= 6
        val doPasswordsMatch = state.password == state.confirmPassword
        
        _uiState.value = state.copy(
            emailError = if (!isValidEmail) "Correo inválido" else null,
            passwordError = if (!isPasswordValid) "Mínimo 6 caracteres" else null,
            confirmPasswordError = if (!doPasswordsMatch) "Las contraseñas no coinciden" else null
        )

        return isValidEmail && isPasswordValid && doPasswordsMatch && state.fullName.isNotBlank() && state.petName.isNotBlank()
    }
}

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val petName: String = "",
    val petType: String = "Gato",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isRegistrationSuccess: Boolean = false
)

sealed class RegisterUiEvent {
    data class FullNameChanged(val fullName: String) : RegisterUiEvent()
    data class EmailChanged(val email: String) : RegisterUiEvent()
    data class PasswordChanged(val password: String) : RegisterUiEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterUiEvent()
    data class PetNameChanged(val petName: String) : RegisterUiEvent()
    data class PetTypeChanged(val petType: String) : RegisterUiEvent()
    object Submit : RegisterUiEvent()
}
