package com.bookstoresimple.app.cache.db

import androidx.room.Room
import com.bookstoresimple.app.TestDataFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
open class BookDatabaseTest {

    private lateinit var db: BookDatabase

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.baseContext,
            BookDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @Test
    fun clearBookList() {

        val mockBookTableList = TestDataFactory.makeBookTableList(10)

        db.bookDao().saveBookList(mockBookTableList)
        db.bookDao().clearBookList()

        val resultBookTableList = db.bookDao().getBookList()

        assert(resultBookTableList.isEmpty())
    }

    @Test
    fun saveBookList() {

        val mockBookTableList = TestDataFactory.makeBookTableList(10)

        db.bookDao().saveBookList(mockBookTableList)

        val resultBookTableList = db.bookDao().getBookList()

        assert(resultBookTableList.isNotEmpty())
    }

    @Test
    fun getBookList() {

        val mockBookTableList = TestDataFactory.makeBookTableList(10)

        db.bookDao().saveBookList(mockBookTableList)

        val resultBookTableList = db.bookDao().getBookList()

        assert(mockBookTableList.size == resultBookTableList.size)
    }

    @After
    fun closeDb() {
        db.close()
    }
}