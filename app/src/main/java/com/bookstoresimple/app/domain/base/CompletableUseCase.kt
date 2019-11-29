package com.bookstoresimple.app.domain.base

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposables

abstract class CompletableUseCase<in Params> protected constructor(
    private val processScheduler: Scheduler,
    private val mainScheduler: Scheduler
) {

    private val subscription = Disposables.empty()

    protected abstract fun buildUseCaseObservable(params: Params): Completable

    fun execute(params: Params): Completable {
        return this.buildUseCaseObservable(params)
            .subscribeOn(processScheduler)
            .observeOn(mainScheduler)
    }

    fun unsubscribe() {
        if (!subscription.isDisposed) {
            subscription.dispose()
        }
    }

}