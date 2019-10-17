package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.data.repository.BookDataStore
import com.bookstoresimple.app.data.repository.BookRemote
import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class BookRemoteDataStore(
    private val bookRemote: BookRemote
) : BookDataStore {

    override fun clearBookList(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookRemote.getBookList()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}