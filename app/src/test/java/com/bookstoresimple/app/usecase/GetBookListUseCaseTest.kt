package com.bookstoresimple.app.usecase

import com.bookstoresimple.app.TestDataFactory
import com.bookstoresimple.app.domain.repository.BookRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetBookListUseCaseTest {

    private lateinit var mockScheduler: Scheduler

    private lateinit var mockBookRepository: BookRepository

    private lateinit var getBookListUseCase: GetBookListUseCase

    @Before
    fun setUp() {

        mockScheduler = Schedulers.trampoline()

        mockBookRepository = mock()

        getBookListUseCase = GetBookListUseCase(
            mockBookRepository,
            mockScheduler,
            mockScheduler
        )
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {

        getBookListUseCase.buildUseCaseObservable(null)
        verify(mockBookRepository).getBookList()
    }

    @Test
    fun buildUseCaseObservableCompletes() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookRepository.getBookList())
            .doReturn(Flowable.just(mockBookList))

        val testObserver = getBookListUseCase.buildUseCaseObservable(null).test()

        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {

        val mockBookList = TestDataFactory.makeBookList()

        whenever(mockBookRepository.getBookList())
            .doReturn(Flowable.just(mockBookList))

        val testObserver = getBookListUseCase.buildUseCaseObservable(null).test()

        testObserver.assertValue(mockBookList)
    }

    @After
    fun tearDown() {

        getBookListUseCase.dispose()
    }
}