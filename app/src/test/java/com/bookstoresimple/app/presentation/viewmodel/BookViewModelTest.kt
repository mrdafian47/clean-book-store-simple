package com.bookstoresimple.app.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.presentation.data.ResourceState
import com.bookstoresimple.app.usecase.GetBookListUseCase
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BookViewModelTest {

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Book>>>

    private lateinit var mockUseCase: GetBookListUseCase

    private lateinit var viewModel: BookViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        captor = argumentCaptor()
        mockUseCase = mock()
        viewModel = BookViewModel(mockUseCase)
    }

    @Test
    fun fetchBookList_withUseCase() {

        viewModel.fetchBookList()
        verify(mockUseCase, times(1)).execute(any(), anyOrNull())
    }

    @Test
    fun fetchBookList_withCall() {

        val mockBookList = TestDataFactory.makeBookList()

        viewModel.fetchBookList()

        verify(mockUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(mockBookList)

        assert(viewModel.bookList.value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun fetchBookList_withResultDataOnSuccess() {

        val mockBookList = TestDataFactory.makeBookList()

        viewModel.fetchBookList()

        verify(mockUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(mockBookList)

        assert(viewModel.bookList.value?.data == mockBookList)
    }

    @Test
    fun fetchBookList_withNoErrorOnSuccess() {

        val mockBookList = TestDataFactory.makeBookList()

        viewModel.fetchBookList()

        verify(mockUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(mockBookList)

        assert(viewModel.bookList.value?.throwable == null)
    }

    @Test
    fun fetchBookList_withResultErrorOnFailure() {

        viewModel.fetchBookList()

        verify(mockUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(viewModel.bookList.value?.status == ResourceState.ERROR)
    }

    @Test
    fun fetchBookList_withErrorOnFailure() {

        viewModel.fetchBookList()

        verify(mockUseCase).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(viewModel.bookList.value?.data == null)
    }

    @Test
    fun fetchBookList_withLoading() {

        viewModel.fetchBookList()

        assert(viewModel.bookList.value?.status == ResourceState.LOADING)
    }
}