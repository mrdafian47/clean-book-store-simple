package com.bookstoresimple.app.data.repository

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.data.source.BookCacheDataSource
import com.bookstoresimple.app.data.source.BookRemoteDataSource
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookRepositoryImplTest {

    private lateinit var mockBookCacheDataSource: BookCacheDataSource
    private lateinit var mockBookRemoteDataSource: BookRemoteDataSource

    private lateinit var bookRepositoryImpl: BookRepositoryImpl

    @Before
    fun setUp() {

        mockBookCacheDataSource = mock()
        mockBookRemoteDataSource = mock()

        bookRepositoryImpl = BookRepositoryImpl(
            mockBookCacheDataSource,
            mockBookRemoteDataSource
        )
    }

    @Test
    fun clearBookList() {

        whenever(mockBookCacheDataSource.clearBookList())
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.clearBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.saveBookList(mockBookList).test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList_cacheDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.saveBookList(any()))
            .doReturn(Completable.complete())

        bookRepositoryImpl.saveBookList(mockBookList).test()

        verify(mockBookCacheDataSource).saveBookList(any())
    }

    @Test
    fun getBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookCacheDataSource.isExpired())
            .doReturn(false)

        whenever(mockBookCacheDataSource.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataSource.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    fun getBookList_cacheDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookCacheDataSource.isExpired())
            .doReturn(false)

        whenever(mockBookCacheDataSource.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataSource.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    fun getBookList_remoteDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataSource.isCached())
            .doReturn(Single.just(false))

        whenever(mockBookCacheDataSource.isExpired())
            .doReturn(true)

        whenever(mockBookRemoteDataSource.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataSource.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }
}