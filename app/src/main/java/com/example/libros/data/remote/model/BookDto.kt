package com.example.libros.data.remote.model

import com.example.libros.data.local.entities.BookEntity

data class BookDto(
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: Int?
) {
    fun toBookEntity(): BookEntity {
        return BookEntity(
            title = title ?: "Sin título",
            author = author_name?.firstOrNull() ?: "Autor desconocido",
            year = first_publish_year,
            coverUrl = cover_i?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }
        )
    }
}
