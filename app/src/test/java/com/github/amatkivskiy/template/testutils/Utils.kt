package com.github.amatkivskiy.template.testutils

import io.reactivex.Observable
import java.io.InputStream

fun <T : Any?> Observable<T>.assertCompletedAndGetFirstValue(): T {
    return test()
        .assertValueCount(1)
        .assertNoErrors()
        .assertComplete()
        .values()[0]
}

fun streamFromFile(fileName: String): InputStream {
    return ClassLoader.getSystemClassLoader()
        .getResourceAsStream(fileName)
}