package com.bookstoresimple.app.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.usecase.GetBookListUseCase
import com.bookstoresimple.app.presentation.data.Resource
import com.bookstoresimple.app.presentation.data.ResourceState
import io.reactivex.subscribers.DisposableSubscriber

class BookViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : ViewModel() {

    val bookList = MutableLiveData<Resource<List<Book>>>()

    override fun onCleared() {
        getBookListUseCase.dispose()
        super.onCleared()
    }

    fun fetchBookList() {
        bookList.postValue(
            Resource(ResourceState.LOADING, null, null)
        )
        getBookListUseCase.execute(BookSubscriber())
    }

    inner class BookSubscriber : DisposableSubscriber<List<Book>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<Book>?) {
            bookList.postValue(
                Resource(ResourceState.SUCCESS, t, null)
            )
        }

        override fun onError(t: Throwable?) {
            bookList.postValue(
                Resource(ResourceState.ERROR, null, t)
            )
        }
    }
}