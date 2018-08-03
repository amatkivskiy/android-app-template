package com.github.amatkivskiy.template.domain.usecase

import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class UseCase<V : Any, E : Exception>(private val threadExecutor: ThreadExecutor, private val postExecutionThread: PostExecutionThread) {
    /**
     * {@link UseCase}'s business logic should be implemented here. That is what this {@link
     * UseCase} is designed for.
     *
     * For example: perform login or get available movies.
     * <p>
     * This method should be called only if you need {@link Observable} without configured {@link
     * Schedulers} (Useful when building chains of {@link Observable}s).
     *
     * @return {@link Observable} which encapsulates business logic.
     */
    abstract fun getRawObservable(): Observable<Result<V, E>>

    /**
     * Returns [UseCase.getRawObservable] but with configured [Schedulers].
     *
     * @return [Observable] with configured [Observable.subscribeOn] and [ ][Observable.observeOn].
     */
    fun getConfiguredObservable(): Observable<Result<V, E>> {
        return getRawObservable()
                .observeOn(postExecutionThread.scheduler)
                .subscribeOn(Schedulers.from(threadExecutor))
    }
}