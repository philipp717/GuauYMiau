package com.example.myapplication.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.model.Pet
import com.example.myapplication.ui.AppViewModelProvider
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PastelRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    email: String?,
    viewModel: WelcomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userEmail = email ?: return
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(userEmail) {
        viewModel.loadPets(userEmail)
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
            Text("Bienvenido, $userEmail", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            // API Integration Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("¿Te gustan los perros?", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchRandomDogImage() }) {
                        Text("Ver perro aleatorio (API)")
                    }
                    AnimatedVisibility(visible = uiState.dogImageUrl != null) {
                        uiState.dogImageUrl?.let { url ->
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Perro aleatorio",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(150.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tus Mascotas", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                
                // RESTAURADO: Botón necesario para cumplir el punto H (Recursos Nativos)
                Button(
                    onClick = { navController.navigate("native_features") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Cámara/Vibración")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.addPet(userEmail, "", "Gato")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Añadir Nuevo Registro")
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(uiState.pets, key = { it.id }) { pet ->
                    PetEditItem(
                        pet = pet,
                        onNameChange = { newName ->
                            viewModel.updatePet(pet.copy(name = newName))
                        },
                        onTypeChange = { newType ->
                            viewModel.updatePet(pet.copy(type = newType))
                        },
                        onPetDelete = {
                            viewModel.deletePet(pet)
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
        // WelcomeScreen(rememberNavController(), "usuario@duoc.cl")
    }
}
