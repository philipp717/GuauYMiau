package com.example.myapplication.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Pet
import com.example.myapplication.data.UserRepository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PastelRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavController, email: String?) {
    val userEmail = email ?: return
    var pets by remember { mutableStateOf(UserRepository.getPets(userEmail).toList()) }

    fun refreshPets() {
        pets = UserRepository.getPets(userEmail).toList()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Registro de Mascotas", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = UserRepository.addPet(userEmail, "", "Gato")
                    if (newPet != null) {
                        pets = pets + newPet
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Añadir Nuevo Registro")
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(pets, key = { it.id }) { pet ->
                    PetEditItem(
                        pet = pet,
                        onNameChange = { newName ->
                            val updatedPet = pet.copy(name = newName)
                            // First, update the list in the UI to ensure responsiveness
                            pets = pets.map { if (it.id == pet.id) updatedPet else it }
                            // Then, update the data in the repository
                            val user = UserRepository.findUser(userEmail)
                            val petIndex = user?.pets?.indexOfFirst { it.id == pet.id }
                            if (user != null && petIndex != null && petIndex != -1) {
                                user.pets[petIndex] = updatedPet
                            }
                        },
                        onTypeChange = { newType ->
                            val updatedPet = pet.copy(type = newType)
                            pets = pets.map { if (it.id == pet.id) updatedPet else it }

                            val user = UserRepository.findUser(userEmail)
                            val petIndex = user?.pets?.indexOfFirst { it.id == pet.id }
                            if (user != null && petIndex != null && petIndex != -1) {
                                user.pets[petIndex] = updatedPet
                            }
                        },
                        onPetDelete = {
                            UserRepository.removePet(userEmail, pet.id)
                            pets = pets.filter { it.id != pet.id }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetEditItem(
    pet: Pet,
    onNameChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onPetDelete: () -> Unit
) {
    var isPetTypeExpanded by remember { mutableStateOf(false) }
    val petTypes = listOf("Gato", "Perro", "Ave", "Otro")

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = pet.name,
                onValueChange = onNameChange,
                label = { Text("Nombre de la Mascota") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
            ExposedDropdownMenuBox(expanded = isPetTypeExpanded, onExpandedChange = { isPetTypeExpanded = it }) {
                OutlinedTextField(
                    value = pet.type,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Mascota") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPetTypeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = textFieldColors
                )
                ExposedDropdownMenu(expanded = isPetTypeExpanded, onDismissRequest = { isPetTypeExpanded = false }) {
                    petTypes.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            onTypeChange(it)
                            isPetTypeExpanded = false
                        })
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = onPetDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = PastelRed)
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MyApplicationTheme {
        WelcomeScreen(rememberNavController(), "usuario@duoc.cl")
    }
}
