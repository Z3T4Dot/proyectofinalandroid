package com.example.libros.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.libros.presentation.viewmodels.UserViewModel
import android.util.Patterns

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by userViewModel.loginSuccess.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()

    val isEmailValid = isValidEmail(email)
    val isPasswordValid = isValidPassword(password)
    val canLogin = isEmailValid && isPasswordValid

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            userViewModel.resetStatus()
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

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
                userViewModel.loginUser(email, password) { found ->
                    if (!found) {
                        userViewModel.setError("Usuario no encontrado")
                    }
                }
            },
            enabled = canLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}
