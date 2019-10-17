package com.bookstoresimple.app.data.repository

import com.bookstoresimple.app.data.store.BookDataStoreFactory
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.domain.repository.BookRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class BookRepositoryImpl(
    private val bookDataStoreFactory: BookDataStoreFactory
) : BookRepository {

    override fun clearBookList(): Completable {
        return bookDataStoreFactory.retrieveCacheDataStore().clearBookList()
    }

    override fun saveBookList(bookList: List<Book>): Completable {
        return bookDataStoreFactory.retrieveCacheDataStore()
            .saveBookList(bookList)
    }

    override fun getBookList(): Flowable<List<Book>> {
        return bookDataStoreFactory.retrieveCacheDataStore()
            .isCached()
            .flatMapPublisher {
                bookDataStoreFactory.retrieveDataStore(it)
                    .getBookList()
            }
            .flatMap {
                saveBookList(it)
                    .toSingle { it }
                    .toFlowable()
            }
    }
}