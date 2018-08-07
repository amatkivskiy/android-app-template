package com.github.amatkivskiy.template.util

import com.github.kittinunf.result.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

fun <V : Any, E : Exception> Result<V, E>.isSuccessful(): Boolean {
    return this is Result.Success
}

fun <T : Any> Observable<T>.toResult(): Observable<Result<T, Exception>> {
    return compose(ObservableToResultTransformer())
}

class ObservableToResultTransformer<T : Any> : ObservableTransformer<T, Result<T, Exception>> {
    override fun apply(upstream: Observable<T>): ObservableSource<Result<T, Exception>> {
        return upstream.map { Result.of(it) }
            .onErrorReturn { Result.error(Exception(it)) }
    }
}