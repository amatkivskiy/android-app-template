package com.github.amatkivskiy.template.domain.usecase

import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.amatkivskiy.template.util.checkNotNull
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class UseCase<V : Any, E : Exception>(private val threadExecutor: ThreadExecutor? = null, private val postExecutionThread: PostExecutionThread? = null) {
    /**
     * {@link UseCase}'s business logic should be implemented here. That is what this {@link
     * UseCase} is designed for.
     *
     * For example: perform login or get available VODs.
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
        checkNotNull(threadExecutor, "threadExecutor")
        checkNotNull(postExecutionThread, "postExecutionThread")

        return getRawObservable().subscribeOn(Schedulers.from(threadExecutor!!))
                .observeOn(postExecutionThread?.scheduler)
    }
}