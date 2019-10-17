package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.data.repository.BookCache
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BookDataStoreFactoryTest {

    private lateinit var mockBookCache: BookCache
    private lateinit var mockBookCacheDataStore: BookCacheDataStore
    private lateinit var mockBookRemoteDataStore: BookRemoteDataStore

    private lateinit var bookDataStoreFactory: BookDataStoreFactory

    @Before
    fun setUp() {

        mockBookCache = mock()
        mockBookCacheDataStore = mock()
        mockBookRemoteDataStore = mock()

        bookDataStoreFactory = BookDataStoreFactory(
            mockBookCache,
            mockBookCacheDataStore,
            mockBookRemoteDataStore
        )
    }

    @Test
    fun retrieveDataStore_WhenNotCached_ReturnRemoteDataSource() {

        whenever(mockBookCache.isCached())
            .doReturn(Single.just(false))

        val bookDataSource = bookDataStoreFactory.retrieveDataStore(false)

        assert(bookDataSource is BookRemoteDataStore)
    }

    @Test
    fun retrieveDataStore_WhenCachedExpired_ReturnRemoteDataSource() {

        whenever(mockBookCache.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookCache.isExpired())
            .doReturn(true)

        val bookDataSource = bookDataStoreFactory.retrieveDataStore(true)

        assert(bookDataSource is BookRemoteDataStore)
    }

    @Test
    fun retrieveDataStore_ReturnCacheDataSource() {

        whenever(mockBookCache.isCached())
            .doReturn(Single.just(true))

        whenever(mockBookCache.isExpired())
            .doReturn(false)

        val bookDataSource = bookDataStoreFactory.retrieveDataStore(true)

        assert(bookDataSource is BookCacheDataStore)
    }

    @Test
    fun retrieveCacheDataStore() {

        val bookDataSource = bookDataStoreFactory.retrieveCacheDataStore()

        assert(bookDataSource is BookCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStore() {

        val bookDataSource = bookDataStoreFactory.retrieveRemoteDataStore()

        assert(bookDataSource is BookRemoteDataStore)
    }
}