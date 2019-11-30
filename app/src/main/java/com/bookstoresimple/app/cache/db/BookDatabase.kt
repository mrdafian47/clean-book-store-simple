package com.bookstoresimple.app.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bookstoresimple.app.cache.dao.BookDao
import com.bookstoresimple.app.cache.model.BookTable

@Database(
    entities = [(BookTable::class)],
    version = 2
)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}