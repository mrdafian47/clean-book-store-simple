package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.data.repository.BookCache
import com.bookstoresimple.app.data.repository.BookDataStore
import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

open class BookCacheDataStore(
    private val bookCache: BookCache
) : BookDataStore {

    override fun clearBookList(): Completable {
        return bookCache.clearBookList()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        return bookCache.saveBookList(bookList)
            .doOnComplete {
                bookCache.setLastCacheTime(System.currentTimeMillis())
            }
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookCache.getBookList()
    }

    override fun isCached(): Single<Boolean> {
        return bookCache.isCached()
    }
}