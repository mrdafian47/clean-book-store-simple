package com.bookstoresimple.app.cache

import androidx.room.Room
import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.cache.db.BookDatabase
import com.bookstoresimple.app.cache.preference.BookPreference
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class BookCacheImplTest {

    private lateinit var mockBookDatabase: BookDatabase
    private lateinit var mockBookPreference: BookPreference

    private lateinit var bookCacheImpl: BookCacheImpl

    @Before
    fun setUp() {

        mockBookDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application,
            BookDatabase::class.java
        ).allowMainThreadQueries().build()

        mockBookPreference = BookPreference(
            RuntimeEnvironment.application
        )

        bookCacheImpl = BookCacheImpl(
            mockBookDatabase,
            mockBookPreference
        )
    }

    @Test
    fun clearBookList() {

        val testObserver = bookCacheImpl.clearBookList().test()

        testObserver.assertComplete()
    }

    @Test
    fun saveBookList() {

        val mockBookList = TestDataFactory.makeBookList()

        val testObserver = bookCacheImpl.saveBookList(mockBookList).test()

        testObserver.assertComplete()
    }

    @Test
    fun getBookList() {

        val testObserver = bookCacheImpl.getBookList().test()

        testObserver.assertComplete()
    }
}