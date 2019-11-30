package com.bookstoresimple.app.cache

import androidx.room.Room
import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.cache.db.BookDatabase
import com.bookstoresimple.app.cache.preference.BookPreference
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class BookCacheDataSourceImplTest {

    private lateinit var mockBookDatabase: BookDatabase
    private lateinit var mockBookPreference: BookPreference

    private lateinit var bookCacheImpl: BookCacheDataSourceImpl

    @Before
    fun setUp() {

        mockBookDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application,
            BookDatabase::class.java
        ).allowMainThreadQueries().build()

        mockBookPreference = BookPreference(
            RuntimeEnvironment.application
        )

        bookCacheImpl = BookCacheDataSourceImpl(
            mockBookDatabase,
            mockBookPreference
        )
    }

    @Test
    fun clearBookList_withComplete() {

        val testObserver = bookCacheImpl.clearBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList_withComplete() {

        val mockBookList = TestDataFactory.makeBookList()

        val testObserver = bookCacheImpl.saveBookList(mockBookList).test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList_withData() {

        val bookCount = 10
        val mockBookList = TestDataFactory.makeBookList(bookCount)

        bookCacheImpl.saveBookList(mockBookList).test()

        val numberOfRows = mockBookDatabase.bookDao().getBookList().size
        assertEquals(bookCount, numberOfRows)
    }

    @Test
    fun getBookList_withComplete() {

        val testObserver = bookCacheImpl.getBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun getBookList_withData() {

        val mockBookList = TestDataFactory.makeBookList()

        bookCacheImpl.saveBookList(mockBookList).test()

        val testObserver = bookCacheImpl.getBookList().test()

        testObserver.assertComplete()
        testObserver.assertValue(mockBookList)
    }

    @Test
    fun isCached_withTrue() {

        val isCached = true
        val mockBookList = TestDataFactory.makeBookList()

        bookCacheImpl.saveBookList(mockBookList).test()

        val testObserver = bookCacheImpl.isCached().test()

        testObserver.assertValue(isCached)
    }

    @Test
    fun isCached_withFalse() {

        val isCached = false

        val testObserver = bookCacheImpl.isCached().test()

        testObserver.assertValue(isCached)
    }

    @Test
    fun setLastCacheTime() {

        val currentTime = System.currentTimeMillis()

        bookCacheImpl.setLastCacheTime(currentTime)

        assertEquals(mockBookPreference.lastCacheTime, currentTime)
    }

    @Test
    fun isExpired_withFalse() {

        val expirationTime = (60 * 10 * 1000).toLong()
        val currentTime = System.currentTimeMillis() + expirationTime

        bookCacheImpl.setLastCacheTime(currentTime)

        val isExpired = bookCacheImpl.isExpired()

        assertEquals(isExpired, false)
    }

    @Test
    fun isExpired_withTrue() {

        val expirationTime = (120 * 10 * 1000).toLong()
        val currentTime = System.currentTimeMillis() - expirationTime

        bookCacheImpl.setLastCacheTime(currentTime)

        val isExpired = bookCacheImpl.isExpired()

        assertEquals(isExpired, true)
    }
}