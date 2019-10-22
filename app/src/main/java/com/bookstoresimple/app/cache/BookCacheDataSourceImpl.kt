package com.bookstoresimple.app.cache

import com.bookstoresimple.app.cache.db.BookDatabase
import com.bookstoresimple.app.cache.model.BookTable
import com.bookstoresimple.app.cache.preference.BookPreference
import com.bookstoresimple.app.data.source.BookCacheDataSource
import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class BookCacheDataSourceImpl(
    private val bookDatabase: BookDatabase,
    private val bookPreference: BookPreference
) : BookCacheDataSource {

    companion object {
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    internal fun getDatabase() = bookDatabase

    override fun clearBookList(): Completable {
        return Completable.defer {
            bookDatabase.bookDao()
                .clearBookList()
            Completable.complete()
        }
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        return Completable.defer {
            bookDatabase.bookDao()
                .saveBookList(
                    bookList
                        .map {
                        BookTable(
                            it.title,
                            it.price,
                            it.description,
                            it.url,
                            it.coverUrl
                        )
                    }
                )
            Completable.complete()
        }
    }

    override fun getBookList(): Flowable<List<Book>> {
        return Flowable.defer {
            Flowable.just(bookDatabase.bookDao()
                .getBookList()
                .map {
                    Book(
                        it.title,
                        it.price,
                        it.description,
                        it.url,
                        it.coverUrl
                    )
                }
            )
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(bookDatabase.bookDao()
                .getBookList()
                .isNotEmpty()
            )
        }
    }

    override fun setLastCacheTime(lastCacheTime: Long) {
        bookPreference.lastCacheTime = lastCacheTime
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return bookPreference.lastCacheTime
    }
}