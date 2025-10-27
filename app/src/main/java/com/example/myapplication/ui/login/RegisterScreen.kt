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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.data.UserRepository
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PastelRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var petName by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var isPetTypeExpanded by remember { mutableStateOf(false) }
    val petTypes = listOf("Gato", "Perro", "Ave", "Otro")

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var petNameError by remember { mutableStateOf<String?>(null) }
    var petTypeError by remember { mutableStateOf<String?>(null) }

    fun validateFullName() { /* ... */ }
    fun validateEmail() { /* ... */ }
    fun validatePassword() { /* ... */ }
    fun validateConfirmPassword() { /* ... */ }
    fun validatePetName() { /* ... */ }
    fun validatePetType() { /* ... */ }

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

            OutlinedTextField(value = fullName, onValueChange = { fullName = it; validateFullName() }, label = { Text("Nombre Completo") }, isError = fullNameError != null, supportingText = { fullNameError?.let { Text(it) } }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            OutlinedTextField(value = email, onValueChange = { email = it; validateEmail() }, label = { Text("Correo Electrónico") }, isError = emailError != null, supportingText = { emailError?.let { Text(it) } }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            OutlinedTextField(value = password, onValueChange = { password = it; validatePassword() }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), isError = passwordError != null, supportingText = { passwordError?.let { Text(it) } }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it; validateConfirmPassword() }, label = { Text("Confirmar Contraseña") }, visualTransformation = PasswordVisualTransformation(), isError = confirmPasswordError != null, supportingText = { confirmPasswordError?.let { Text(it) } }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono (Opcional)") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Registro de Mascotas", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            ExposedDropdownMenuBox(expanded = isPetTypeExpanded, onExpandedChange = { isPetTypeExpanded = it }, modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = petType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Mascota") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPetTypeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    isError = petTypeError != null,
                    supportingText = { petTypeError?.let { Text(it) } },
                    colors = textFieldColors
                )
                ExposedDropdownMenu(expanded = isPetTypeExpanded, onDismissRequest = { isPetTypeExpanded = false }) {
                    petTypes.forEach {
                        DropdownMenuItem(text = { Text(it) }, onClick = {
                            petType = it
                            validatePetType()
                            isPetTypeExpanded = false
                        })
                    }
                }
            }
            OutlinedTextField(value = petName, onValueChange = { petName = it; validatePetName() }, label = { Text("Nombre de la Mascota") }, isError = petNameError != null, supportingText = { petNameError?.let { Text(it) } }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Validation logic...
                    if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null && petNameError == null && petTypeError == null) {
                        val success = UserRepository.registerUser(email, password, petName, petType)
                        if (success) {
                            navController.navigate("login")
                        } else {
                            emailError = "El correo ya está en uso"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Registrarse")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MyApplicationTheme {
        RegisterScreen(rememberNavController())
    }
}
