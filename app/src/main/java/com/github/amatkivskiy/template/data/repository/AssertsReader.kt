package com.github.amatkivskiy.template.data.repository

import java.io.IOException
import java.io.InputStream

interface AssertsReader {
    @Throws(IOException::class)
    fun readDataFromAssert(assertName: String): InputStream
}