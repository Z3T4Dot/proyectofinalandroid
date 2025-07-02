package com.example.libros.ui.screen

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.libros.presentation.viewmodels.UserViewModel

@Composable
fun RegisterScreen(
    userViewModel: UserViewModel,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by userViewModel.loginSuccess.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()

    val isEmailValid = isValidEmailRegister(email)
    val isPasswordValid = isValidPasswordRegister(password)
    val canRegister = isEmailValid && isPasswordValid

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            userViewModel.resetStatus()
            navController.navigate("home") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro de Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (!isEmailValid && email.isNotEmpty()) {
            Text("Correo no válido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (!isPasswordValid && password.isNotEmpty()) {
            Text("Contraseña mínima de 6 caracteres", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                userViewModel.registerUser(email, password)
            },
            enabled = canRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}

// 🔍 Validadores
fun isValidEmailRegister(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPasswordRegister(password: String): Boolean {
    return password.length >= 6
}
