package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.data.repository.BookRemote
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookRemoteDataStoreTest {

    private lateinit var mockBookRemote: BookRemote

    private lateinit var bookRemoteDataStore: BookRemoteDataStore

    @Before
    fun setUp() {

        mockBookRemote = mock()
        bookRemoteDataStore = BookRemoteDataStore(mockBookRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearBookList() {

        bookRemoteDataStore.clearBookList().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        bookRemoteDataStore.saveBookList(mockBookList).test()
    }

    @Test
    fun getBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookRemote.getBookList())
            .doReturn(Flowable.just(mockBookList))

        val testObserver = bookRemoteDataStore.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun isCached() {

        bookRemoteDataStore.isCached().test()
    }
}