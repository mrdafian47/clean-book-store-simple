package com.bookstoresimple.app.remote

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.remote.service.BookService
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookRemoteDataSourceImplTest {

    private lateinit var mockBookService: BookService

    private lateinit var bookRemoteImpl: BookRemoteDataSourceImpl

    @Before
    fun setUp() {

        mockBookService = mock()
        bookRemoteImpl = BookRemoteDataSourceImpl(mockBookService)
    }

    @Test
    fun getBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookService.getBookList())
            .doReturn(Flowable.just(mockBookList))

        val testObserver = bookRemoteImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }
}