package com.bookstoresimple.app.presentation.data

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.domain.model.Book
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.RuntimeException

@RunWith(JUnit4::class)
class ResourceTest {

    @Test
    fun loading_withResult() {

        val resource = Resource.loading<List<Book>>()

        assert(resource.status == ResourceState.LOADING)
        assert(resource.data == null)
        assert(resource.throwable == null)
    }

    @Test
    fun success_withResult() {

        val mockBookList = TestDataFactory.makeBookList()
        val resource = Resource.success(mockBookList)

        assert(resource.status == ResourceState.SUCCESS)
        assert(resource.data == mockBookList)
        assert(resource.throwable == null)
    }

    @Test
    fun error_withResult() {

        val mockError = RuntimeException("error")
        val resource = Resource.error<List<Book>>(mockError)

        assert(resource.status == ResourceState.ERROR)
        assert(resource.data == null)
        assert(resource.throwable == mockError)
    }
}