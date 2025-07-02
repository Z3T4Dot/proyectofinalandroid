package com.example.libros.data.remote

import com.example.libros.data.remote.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}
