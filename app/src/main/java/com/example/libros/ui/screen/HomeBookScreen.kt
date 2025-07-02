package com.example.libros.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.libros.viewmodel.BookViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(Unit) {
        if (searchResults.isEmpty()) {
            kotlinx.coroutines.delay(500)
            viewModel.loadBooksFromApi()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Libros Comunitarios📚") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("form")
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            item {
                Text(
                    text = "Aquí puedes agregar, editar o eliminar libros que estarán disponibles para toda la comunidad. ¡Tu aporte es valioso! 📖✨",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Mostrar libros guardados
            items(books) { book ->
                BookItem(
                    title = book.title,
                    author = book.author,
                    cover = book.coverUrl,
                    onClick = {
                        navController.navigate("detail/${book.id}")
                    }
                )
            }

            // Mostrar libros sugeridos desde la API (solo si no están guardados)
            items(searchResults) { book ->
                val title = book.title ?: return@items
                val author = book.author_name?.firstOrNull() ?: "Autor desconocido"
                val cover = book.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }

                val isAlreadySaved = books.any { it.title == title && it.author == author }

                if (!isAlreadySaved) {
                    BookItem(
                        title = title,
                        author = author,
                        cover = cover,
                        onClick = {
                            viewModel.addBook(book)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BookItem(title: String, author: String, cover: String?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!cover.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(cover),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, style = MaterialTheme.typography.subtitle1)
            Text(author, style = MaterialTheme.typography.body2)
        }
    }
}




