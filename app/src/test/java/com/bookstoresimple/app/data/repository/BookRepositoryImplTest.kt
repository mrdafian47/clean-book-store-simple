package com.bookstoresimple.app.data.repository

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.data.store.BookCacheDataStore
import com.bookstoresimple.app.data.store.BookDataStoreFactory
import com.bookstoresimple.app.data.store.BookRemoteDataStore
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

    private lateinit var mockBookDataStoreFactory: BookDataStoreFactory
    private lateinit var mockBookCacheDataStore: BookCacheDataStore
    private lateinit var mockBookRemoteDataStore: BookRemoteDataStore

    private lateinit var bookRepositoryImpl: BookRepositoryImpl

    @Before
    fun setUp() {

        mockBookDataStoreFactory = mock()
        mockBookCacheDataStore = mock()
        mockBookRemoteDataStore = mock()

        bookRepositoryImpl =
            BookRepositoryImpl(mockBookDataStoreFactory)

        whenever(mockBookDataStoreFactory.retrieveCacheDataStore())
            .doReturn(mockBookCacheDataStore)

        whenever(mockBookDataStoreFactory.retrieveRemoteDataStore())
            .doReturn(mockBookRemoteDataStore)
    }

    @Test
    fun clearBookList() {

        whenever(mockBookCacheDataStore.clearBookList())
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.clearBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun clearBookList_cacheDataStore() {

        whenever(mockBookCacheDataStore.clearBookList())
            .doReturn(Completable.complete())

        bookRepositoryImpl.clearBookList().test()

        verify(mockBookCacheDataStore).clearBookList()
    }

    @Test
    fun clearBookList_remoteDataStore() {

        whenever(mockBookCacheDataStore.clearBookList())
            .doReturn(Completable.complete())

        bookRepositoryImpl.clearBookList().test()

        verify(mockBookRemoteDataStore, never()).clearBookList()
    }

    @Test
    fun saveBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.saveBookList(mockBookList).test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList_cacheDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        bookRepositoryImpl.saveBookList(mockBookList).test()

        verify(mockBookCacheDataStore).saveBookList(any())
    }

    @Test
    fun saveBookList_remoteDataSource() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        bookRepositoryImpl.saveBookList(mockBookList).test()

        verify(mockBookRemoteDataStore, never()).saveBookList(any())
    }

    @Test
    fun getBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookDataStoreFactory.retrieveDataStore(any()))
            .doReturn(mockBookCacheDataStore)

        whenever(mockBookCacheDataStore.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    fun getBookList_cacheDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookDataStoreFactory.retrieveDataStore(any()))
            .doReturn(mockBookCacheDataStore)

        whenever(mockBookCacheDataStore.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    fun getBookList_remoteDataStore() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookCacheDataStore.isCached())
            .doReturn(Single.just(false))

        whenever(mockBookDataStoreFactory.retrieveDataStore(any()))
            .doReturn(mockBookRemoteDataStore)

        whenever(mockBookRemoteDataStore.getBookList())
            .doReturn(Flowable.just(mockBookList))

        whenever(mockBookCacheDataStore.saveBookList(any()))
            .doReturn(Completable.complete())

        val testObserver = bookRepositoryImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }
}