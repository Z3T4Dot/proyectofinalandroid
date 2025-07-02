package com.example.libros.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.libros.viewmodel.BookViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: BookViewModel, bookId: Int?) {
    val book = viewModel.books.collectAsState().value.find { it.id == bookId }

    var showDialog by remember { mutableStateOf(false) }

    if (book == null) {
        Text("Libro no encontrado", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(book.title) })
        },
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("form/${book.id}")
                    }
                ) {
                    Text("✏️")
                }
                FloatingActionButton(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Text("🗑️")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (book.coverUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(book.coverUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text("Título: ${book.title}", style = MaterialTheme.typography.h6)
            Text("Autor: ${book.author}", style = MaterialTheme.typography.body1)
            Text("Año: ${book.year ?: "Desconocido"}", style = MaterialTheme.typography.body2)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Eliminar libro?") },
            text = { Text("¿Estás seguro de que quieres eliminar \"${book.title}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBook(book)
                    showDialog = false
                    navController.popBackStack()
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

