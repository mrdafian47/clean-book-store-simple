package com.bookstoresimple.app.data.store

import com.bookstoresimple.app.data.source.BookRemoteDataSource
import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class BookRemoteDataStore(
    private val bookRemoteDataSource: BookRemoteDataSource
) : BookDataStore {

    override fun clearBookList(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookRemoteDataSource.getBookList()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}