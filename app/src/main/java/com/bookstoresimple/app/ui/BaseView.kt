package com.bookstoresimple.app.ui

import android.os.Bundle
import com.bookstoresimple.app.presentation.data.Resource
import com.bookstoresimple.app.presentation.data.ResourceState

interface BaseView {

    fun initView(savedInstanceState: Bundle?)

    fun initEvent()

    fun loadingData()

    fun observeData()

    fun startLoading()

    fun stopLoading()

    fun <T> parseObserveData(
        resource: Resource<T>,
        resultLoading: (T?) -> Unit = { startLoading() },
        resultSuccess: (T) -> Unit = { _: T -> },
        resultError: (Throwable?) -> Unit = { _: Throwable? -> }
    ) {
        when (resource.status) {
            ResourceState.LOADING -> {
                resultLoading(resource.data)
            }
            ResourceState.SUCCESS -> {
                stopLoading()
                resource.data?.let { resultSuccess(it) }
            }
            ResourceState.ERROR -> {
                stopLoading()
                resultError(resource.throwable)
            }
        }
    }
}