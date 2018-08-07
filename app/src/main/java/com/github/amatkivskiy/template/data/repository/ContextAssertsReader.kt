package com.github.amatkivskiy.template.data.repository

import android.content.Context
import java.io.InputStream

class ContextAssertsReader(private val context: Context) : AssertsReader {
    override fun readDataFromAssert(assertName: String): InputStream = context.assets.open(assertName)
}