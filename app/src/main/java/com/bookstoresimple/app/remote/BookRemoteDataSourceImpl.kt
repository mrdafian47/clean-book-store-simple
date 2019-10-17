package com.bookstoresimple.app.remote

import com.bookstoresimple.app.data.source.BookRemoteDataSource
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.remote.service.BookService
import io.reactivex.Flowable

class BookRemoteDataSourceImpl(
    private val bookService: BookService
) : BookRemoteDataSource {

    override fun getBookList(): Flowable<List<Book>> {
        return bookService.getBookList()
            .map { bookList ->
                bookList.map {
                    Book(
                        it.title,
                        it.price,
                        it.description,
                        it.url,
                        it.coverUrl
                    )
                }
            }
    }
}