package com.bookstoresimple.app.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_book")
data class BookTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val url: String,
    val coverUrl: String
)