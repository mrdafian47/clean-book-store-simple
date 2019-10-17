package com.bookstoresimple.app.data.store

import com.bookstoresimple.app.data.source.BookCacheDataSource
import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class BookCacheDataStore(
    private val bookCacheDataSource: BookCacheDataSource
) : BookDataStore {

    override fun clearBookList(): Completable {
        return bookCacheDataSource.clearBookList()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        return bookCacheDataSource.saveBookList(bookList)
            .doOnComplete {
                bookCacheDataSource.setLastCacheTime(System.currentTimeMillis())
            }
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookCacheDataSource.getBookList()
    }

    override fun isCached(): Single<Boolean> {
        return bookCacheDataSource.isCached()
    }
}