package com.example.myapplication.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.network.DogApiService
import com.example.myapplication.model.Pet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val userRepository: UserRepository,
    private val dogApiService: DogApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun loadPets(email: String) {
        viewModelScope.launch {
            userRepository.getPetsStream(email).collectLatest { pets ->
                _uiState.value = _uiState.value.copy(pets = pets)
            }
        }
    }

    fun addPet(email: String, name: String, type: String) {
        viewModelScope.launch {
            userRepository.addPet(Pet(name = name, type = type, ownerEmail = email))
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            userRepository.deletePet(pet)
        }
    }

    fun updatePet(pet: Pet) {
         viewModelScope.launch {
             userRepository.updatePet(pet)
         }
    }

    fun fetchRandomDogImage() {
        viewModelScope.launch {
            try {
                val response = dogApiService.getRandomDogImage()
                _uiState.value = _uiState.value.copy(dogImageUrl = response.message)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

data class WelcomeUiState(
    val pets: List<Pet> = emptyList(),
    val dogImageUrl: String? = null
)
