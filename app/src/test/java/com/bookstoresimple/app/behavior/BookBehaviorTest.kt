package com.bookstoresimple.app.behavior

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.presentation.data.ResourceState
import com.bookstoresimple.app.presentation.viewmodel.BookViewModel
import com.bookstoresimple.app.usecase.GetBookListUseCase
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.Spec
import io.kotlintest.extensions.TopLevelTest
import io.kotlintest.specs.BehaviorSpec
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Rule
import org.mockito.Captor

class BookBehaviorTest : BehaviorSpec() {

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Book>>>

    private lateinit var mockUseCase: GetBookListUseCase

    private lateinit var viewModel: BookViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun beforeSpecClass(spec: Spec, tests: List<TopLevelTest>) {
        super.beforeSpecClass(spec, tests)
        captor = argumentCaptor()
        mockUseCase = mock()
        viewModel = BookViewModel(mockUseCase)
    }

    init {

        given("I enter the apps") {

            val mockBookList = TestDataFactory.makeBookList()

            `when`("I see the Book List screen") {

                viewModel.fetchBookList()

                then("The loading indicator should display") {

                    assert(viewModel.bookList.value?.status == ResourceState.LOADING)
                }

                and("The book list should display") {

                    verify(mockUseCase).execute(captor.capture(), eq(null))
                    captor.firstValue.onNext(mockBookList)

                    assert(viewModel.bookList.value?.status == ResourceState.SUCCESS)
                    assert(viewModel.bookList.value?.data == mockBookList)
                }
            }
        }
        resetFieldState()
    }

    private fun resetFieldState() {
        captor = argumentCaptor()
        mockUseCase = mock()
        viewModel = BookViewModel(mockUseCase)
    }
}