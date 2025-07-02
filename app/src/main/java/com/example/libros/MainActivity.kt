package com.example.libros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.libros.data.local.BookDatabase
import com.example.libros.data.local.dao.BookDao
import com.example.libros.presentation.viewmodels.UserViewModel
import com.example.libros.presentation.viewmodels.UserViewModelFactory
import com.example.libros.ui.NavigationGraph
import com.example.libros.viewmodel.BookViewModel
import com.example.libros.viewmodel.BookViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "libros-db"
        ).build()

        val userViewModel = UserViewModelFactory(db.userDao()).create(UserViewModel::class.java)
        val bookViewModel = BookViewModelFactory(db.bookDao()).create(BookViewModel::class.java)

        setContent {
            val navController = rememberNavController()

            NavigationGraph(
                navController = navController,
                viewModel = bookViewModel,
                userViewModel = userViewModel
            )
        }
    }
}

