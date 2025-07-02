package com.example.libros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.libros.data.local.entities.BookEntity
import com.example.libros.viewmodel.BookViewModel

@Composable
fun FormScreen(navController: NavController, viewModel: BookViewModel, bookId: Int?) {
    val editing = bookId != null
    val bookToEdit = viewModel.books.collectAsState().value.find { it.id == bookId }

    var title by remember { mutableStateOf(bookToEdit?.title ?: "") }
    var author by remember { mutableStateOf(bookToEdit?.author ?: "") }
    var year by remember { mutableStateOf(bookToEdit?.year?.toString() ?: "") }
    var cover by remember { mutableStateOf(bookToEdit?.coverUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(if (editing) "Editar libro" else "Nuevo libro")
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Año") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cover,
                onValueChange = { cover = it },
                label = { Text("URL de la portada") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val finalBook = BookEntity(
                        id = bookToEdit?.id ?: 0,
                        title = title,
                        author = author,
                        year = year.toIntOrNull(),
                        coverUrl = cover.ifBlank { null }
                    )

                    if (editing) viewModel.updateBook(finalBook)
                    else viewModel.insertBook(finalBook)

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editing) "Actualizar" else "Guardar")
            }
        }
    }
}
