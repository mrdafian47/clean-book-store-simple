package com.bookstoresimple.app.data.store

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.data.source.BookCacheDataSource
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookCacheDataStoreTest {

    private lateinit var mockBookCacheDataSource: BookCacheDataSource

    private lateinit var bookCacheDataStore: BookCacheDataStore

    @Before
    fun setUp() {

        mockBookCacheDataSource = mock()
        bookCacheDataStore =
            BookCacheDataStore(mockBookCacheDataSource)
    }

    @Test
    fun clearBookList() {

        whenever(mockBookCacheDataSource.clearBookList())
            .doReturn(Completable.complete())

        val testObserver = bookCacheDataStore.clearBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.saveBookList(mockBookList))
            .doReturn(Completable.complete())

        val testObserver = bookCacheDataStore.saveBookList(mockBookList).test()

        testObserver.assertComplete()
    }

    @Test
    fun getBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.getBookList())
            .doReturn(Flowable.just(mockBookList))

        val testObserver = bookCacheDataStore.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    @Ignore(value = "Misplaced or misused argument matcher")
    fun isCached() {

        whenever(mockBookCacheDataSource.isCached())
            .doReturn(Single.just(true))

        val testObserver = bookCacheDataStore.isCached().test()

        testObserver.assertComplete()
    }
}