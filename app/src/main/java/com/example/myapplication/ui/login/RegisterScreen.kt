package com.example.myapplication.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

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

    fun validateFullName() {
        fullNameError = when {
            fullName.isBlank() -> "El nombre completo no puede estar vacío"
            !fullName.matches(Regex("^[a-zA-Z ]+$")) -> "Solo se permiten caracteres alfabéticos y espacios"
            fullName.length > 50 -> "El nombre completo no puede tener más de 50 caracteres"
            else -> null
        }
    }

    fun validateEmail() {
        emailError = when {
            !email.endsWith("@duoc.cl") -> "El correo debe tener el formato usuario@duoc.cl"
            else -> null
        }
    }

    fun validatePassword() {
        passwordError = when {
            password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            !password.matches(Regex(".*[A-Z].*")) -> "La contraseña debe tener al menos una mayúscula"
            !password.matches(Regex(".*[a-z].*")) -> "La contraseña debe tener al menos una minúscula"
            !password.matches(Regex(".*[0-9].*")) -> "La contraseña debe tener al menos un número"
            !password.matches(Regex(".*[@#\$%].*")) -> "La contraseña debe tener al menos un carácter especial (@#\$%)"
            else -> null
        }
    }

    fun validateConfirmPassword() {
        confirmPasswordError = if (password != confirmPassword) "Las contraseñas no coinciden" else null
    }
    
    fun validatePetName() {
        petNameError = when {
            petName.isBlank() -> "El nombre de la mascota no puede estar vacío"
            petName.length > 50 -> "El nombre de la mascota no puede exceder los 50 caracteres"
            else -> null
        }
    }

    fun validatePetType() {
        petTypeError = if (petType.isBlank()) "Debes seleccionar un tipo de mascota" else null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = fullName, onValueChange = { fullName = it; validateFullName() }, label = { Text("Nombre Completo") }, isError = fullNameError != null, supportingText = { fullNameError?.let { Text(it) } })
        OutlinedTextField(value = email, onValueChange = { email = it; validateEmail() }, label = { Text("Correo Electrónico") }, isError = emailError != null, supportingText = { emailError?.let { Text(it) } })
        OutlinedTextField(
            value = password, 
            onValueChange = { password = it; validatePassword() }, 
            label = { Text("Contraseña") }, 
            visualTransformation = PasswordVisualTransformation(), 
            isError = passwordError != null, 
            supportingText = { passwordError?.let { Text(it) } }
        )
        OutlinedTextField(
            value = confirmPassword, 
            onValueChange = { confirmPassword = it; validateConfirmPassword() }, 
            label = { Text("Confirmar Contraseña") }, 
            visualTransformation = PasswordVisualTransformation(), 
            isError = confirmPasswordError != null, 
            supportingText = { confirmPasswordError?.let { Text(it) } }
        )
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono (Opcional)") })

        // Pet Registration
        Text("Registro de Mascotas", modifier = Modifier.padding(top = 16.dp))
        ExposedDropdownMenuBox(expanded = isPetTypeExpanded, onExpandedChange = { isPetTypeExpanded = it }) {
            OutlinedTextField(
                value = petType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de Mascota") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPetTypeExpanded) },
                modifier = Modifier.menuAnchor(),
                isError = petTypeError != null,
                supportingText = { petTypeError?.let { Text(it) } }
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

        OutlinedTextField(value = petName, onValueChange = { petName = it; validatePetName() }, label = { Text("Nombre de la Mascota") }, isError = petNameError != null, supportingText = { petNameError?.let { Text(it) } })

        Button(onClick = { 
            validateFullName()
            validateEmail()
            validatePassword()
            validateConfirmPassword()
            validatePetName()
            validatePetType()
            
            if (fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null && petNameError == null && petTypeError == null) {
                // TODO: Handle registration
            }
         }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Registrarse")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}
