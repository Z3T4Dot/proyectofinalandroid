package com.example.libros.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libros.data.local.dao.BookDao
import com.example.libros.data.local.entities.BookEntity
import com.example.libros.data.remote.model.BookDto
import com.example.libros.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    private val repository: BookRepository = BookRepository(bookDao)

    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    val books: StateFlow<List<BookEntity>> = _books.asStateFlow()

    private val _searchResults = MutableStateFlow<List<BookDto>>(emptyList())
    val searchResults: StateFlow<List<BookDto>> = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllBooks.collect {
                _books.value = it
            }
        }
    }

    fun addBook(book: BookDto) = viewModelScope.launch {
        val entity = book.toBookEntity()
        repository.insertBook(entity)
    }

    fun insertBook(book: BookEntity) = viewModelScope.launch {
        repository.insertBook(book)
    }

    fun updateBook(book: BookEntity) = viewModelScope.launch {
        repository.updateBook(book)
    }

    fun deleteBook(book: BookEntity) = viewModelScope.launch {
        repository.deleteBook(book)
    }

    fun loadBooksFromApi() = viewModelScope.launch {
        try {
            val result = repository.getBooksFromApi()

            val validResults = result.filterNot {
                it.title.isNullOrEmpty() || it.author_name.isNullOrEmpty()
            }

            if (validResults.isEmpty()) {
                println("⚠️ La API respondió, pero sin libros válidos")
            } else {
                _searchResults.value = validResults
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error API: ${e.message}")
            _searchResults.value = emptyList()
        }
    }
}
