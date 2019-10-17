package com.bookstoresimple.app.domain.repository

import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable

interface BookRepository {

    fun clearBookList(): Completable

    fun saveBookList(bookList: List<Book>): Completable

    fun getBookList(): Flowable<List<Book>>
}