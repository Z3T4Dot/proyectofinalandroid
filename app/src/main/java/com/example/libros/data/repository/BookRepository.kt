package com.example.libros.data.repository

import com.example.libros.data.local.dao.BookDao
import com.example.libros.data.local.entities.BookEntity
import com.example.libros.data.remote.model.BookDto
import com.example.libros.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {

    val getAllBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()

    suspend fun insertBook(book: BookEntity) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }

    suspend fun getBooksFromApi(): List<BookDto> {
        return try {
            val response = RetrofitInstance.api.searchBooks("libros")
            response.docs
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
