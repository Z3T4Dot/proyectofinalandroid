package com.example.libros.ui

import android.R.attr.defaultValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.libros.presentation.viewmodels.UserViewModel
import com.example.libros.ui.screens.DetailScreen
import com.example.libros.ui.screens.FormScreen
import com.example.libros.ui.screen.HomeScreen
import com.example.libros.ui.screen.LoginScreen
import com.example.libros.ui.screen.RegisterScreen
import com.example.libros.ui.screen.WelcomeScreen

import com.example.libros.viewmodel.BookViewModel

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: BookViewModel, userViewModel: UserViewModel ) {
    NavHost(navController, startDestination = "welcome") {

        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("register")  }
            )
        }

        composable("login") {
            LoginScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }

        composable("register") {
            RegisterScreen(
                userViewModel = userViewModel,
                navController = navController
            )
        }


        composable("books") {
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable("home") {
            HomeScreen(navController, viewModel)
        }
        composable("form") {
            FormScreen(navController, viewModel, null)
        }
        composable("form/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            FormScreen(navController, viewModel, id)
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            DetailScreen(navController, viewModel, id)
        }
    }
}


