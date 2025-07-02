package com.example.libros.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.libros.data.local.dao.BookDao
import com.example.libros.data.local.dao.UserDao
import com.example.libros.data.local.entities.BookEntity
import com.example.libros.data.local.entities.UserEntity

@Database(entities = [BookEntity::class, UserEntity::class], version = 2) // 👈 cambia a versión 2
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun userDao(): UserDao
    companion object {
        @Volatile private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}

