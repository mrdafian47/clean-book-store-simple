package com.bookstoresimple.app.usecase

import com.bookstoresimple.app.domain.base.FlowableUseCase
import com.bookstoresimple.app.domain.executor.PostExecutionThread
import com.bookstoresimple.app.domain.executor.ThreadExecutor
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.domain.repository.BookRepository
import io.reactivex.Flowable

open class GetBookListUseCase(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val bookRepository: BookRepository
) : FlowableUseCase<List<Book>, Void?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Flowable<List<Book>> {
        return bookRepository.getBookList()
    }
}