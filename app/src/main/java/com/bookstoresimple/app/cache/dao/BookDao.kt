package com.bookstoresimple.app.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bookstoresimple.app.cache.model.BookTable

@Dao
interface BookDao {

    @Query("DELETE FROM table_book ;")
    fun clearBookList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookList(bookList: List<BookTable>)

    @Query("SELECT * FROM table_book ;")
    fun getBookList(): List<BookTable>
}