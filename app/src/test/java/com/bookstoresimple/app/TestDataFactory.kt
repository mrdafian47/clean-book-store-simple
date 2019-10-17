package com.bookstoresimple.app

import com.bookstoresimple.app.domain.model.Book
import java.util.*

object TestDataFactory {

    fun makeBookList(total: Int = 10): List<Book> {
        val list = mutableListOf<Book>()
        for (i in 0 until total) {
            list.add(makeBook())
        }
        return list
    }

    private fun makeBook(): Book {
        return Book(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        )
    }
}