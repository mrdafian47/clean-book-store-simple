package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Flowable

interface BookRemoteDataSource {

    fun getBookList(): Flowable<List<Book>>
}