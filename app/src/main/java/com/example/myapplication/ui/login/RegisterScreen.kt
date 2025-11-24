@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.AppViewModelProvider
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PastelRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    var isPetTypeExpanded by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) } // Controla el diálogo
    val petTypes = listOf("Gato", "Perro", "Ave", "Otro")

    // Observamos si el registro fue exitoso
    LaunchedEffect(uiState.isRegistrationSuccess) {
        if (uiState.isRegistrationSuccess) {
            showSuccessDialog = true // Mostramos el diálogo en lugar de navegar directo
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.guau_y_miau_logo),
                contentDescription = "Logo Guau&Miau",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                errorSupportingTextColor = PastelRed
            )

            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = { viewModel.onEvent(RegisterUiEvent.FullNameChanged(it)) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(RegisterUiEvent.EmailChanged(it)) },
                label = { Text("Correo Electrónico") },
                isError = uiState.emailError != null,
                supportingText = { uiState.emailError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(RegisterUiEvent.PasswordChanged(it)) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                supportingText = { uiState.passwordError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onEvent(RegisterUiEvent.ConfirmPasswordChanged(it)) },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.confirmPasswordError != null,
                supportingText = { uiState.confirmPasswordError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Registro de Mascotas", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            ExposedDropdownMenuBox(expanded = isPetTypeExpanded, onExpandedChange = { isPetTypeExpanded = it }, modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = uiState.petType,
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
                            viewModel.onEvent(RegisterUiEvent.PetTypeChanged(it))
                            isPetTypeExpanded = false
                        })
                    }
                }
            }
            OutlinedTextField(
                value = uiState.petName,
                onValueChange = { viewModel.onEvent(RegisterUiEvent.PetNameChanged(it)) },
                label = { Text("Nombre de la Mascota") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.onEvent(RegisterUiEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Registrarse")
            }

            // --- DIÁLOGO DE FELICITACIONES ---
            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { /* No hacemos nada para obligar a usar el botón */ },
                    title = {
                        Text(
                            text = "¡Felicidades! 🥳",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Te has registrado correctamente en Guau&Miau.",
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "🎉🐶😺🎉",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSuccessDialog = false
                                navController.navigate("login")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ir al Login")
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MyApplicationTheme {
        // RegisterScreen(rememberNavController())
    }
}
