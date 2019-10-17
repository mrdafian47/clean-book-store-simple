package com.bookstoresimple.app.domain.model

data class Book(
    val title: String,
    val price: String,
    val description: String,
    val url: String,
    val coverUrl: String
)