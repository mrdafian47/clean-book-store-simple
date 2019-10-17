package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookCacheDataSource {

    fun clearBookList(): Completable

    fun saveBookList(bookList: List<Book>): Completable

    fun getBookList(): Flowable<List<Book>>

    fun isCached(): Single<Boolean>

    fun setLastCacheTime(lastCacheTime: Long)

    fun isExpired(): Boolean
}