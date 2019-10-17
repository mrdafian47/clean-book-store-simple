package com.bookstoresimple.app.remote

import com.bookstoresimple.app.data.repository.BookRemote
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.remote.service.BookService
import io.reactivex.Flowable

class BookRemoteImpl(
    private val bookService: BookService
) : BookRemote {

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