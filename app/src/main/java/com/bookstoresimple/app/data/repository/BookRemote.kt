package com.bookstoresimple.app.data.repository

import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Flowable

interface BookRemote {

    fun getBookList(): Flowable<List<Book>>
}