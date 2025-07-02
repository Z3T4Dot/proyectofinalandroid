package com.example.libros.viewmodel

import com.example.libros.data.local.dao.BookDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
