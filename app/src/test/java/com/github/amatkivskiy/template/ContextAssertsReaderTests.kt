package com.github.amatkivskiy.template

import android.content.Context
import android.content.res.AssetManager
import com.github.amatkivskiy.template.data.repository.ContextAssertsReader
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Test
import java.io.InputStream

class ContextAssertsReaderTests {
    @Test
    fun `test proper asserts are read from Context`() {
        val (mockedContext, mockedAssets) = createMockedValues()
        val mockedStream = mock<InputStream>()

        val assertName = "file_name"
        whenever(mockedAssets.open(assertName)).thenReturn(mockedStream)

        ContextAssertsReader(mockedContext)
                .readDataFromAssert(assertName)
                .`should equal`(mockedStream)
    }

    private fun createMockedValues(): Pair<Context, AssetManager> {
        val mockedContext = mock<Context>()
        val mockedAssets = mock<AssetManager>()
        whenever(mockedContext.assets).thenReturn(mockedAssets)

        return Pair(mockedContext, mockedAssets)
    }
}