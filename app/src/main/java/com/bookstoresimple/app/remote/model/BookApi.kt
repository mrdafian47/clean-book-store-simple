package com.bookstoresimple.app.remote.model

import com.google.gson.annotations.SerializedName

data class BookApi(
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("price")
    val price: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("coverUrl")
    val coverUrl: String
)