package com.github.amatkivskiy.template.util

import com.github.kittinunf.result.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

fun <V : Any, E : Exception> Result<V, E>.isSuccessful(): Boolean {
    return this is Result.Success
}

fun <V : Any, E : Exception> Result<V, E>.isSuccessfulNonEmpty(): Boolean {
    return this is Result.Success && this.component1() != null
}

fun <T : Any> Observable<T>.toResult(): Observable<Result<T, Exception>> {
    return compose(ObservableToResultTransformer())
}

fun <T : Any> T?.whenNotNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

class ObservableToResultTransformer<T : Any> : ObservableTransformer<T, Result<T, Exception>> {
    override fun apply(upstream: Observable<T>): ObservableSource<Result<T, Exception>> {
        return upstream.map { Result.of(it) }
            .onErrorReturn { Result.error(Exception(it)) }
    }
}