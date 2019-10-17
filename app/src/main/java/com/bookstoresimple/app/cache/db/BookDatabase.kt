package com.bookstoresimple.app.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bookstoresimple.app.cache.dao.BookDao
import com.bookstoresimple.app.cache.model.BookTable

@Database(
    entities = [(BookTable::class)],
    version = 1
)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    private var INSTANCE: BookDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): BookDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book.db"
                    ).build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }
}