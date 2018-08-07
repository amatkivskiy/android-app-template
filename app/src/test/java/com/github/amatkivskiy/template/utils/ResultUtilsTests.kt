package com.github.amatkivskiy.template.utils

import com.github.amatkivskiy.template.util.isSuccessful
import com.github.kittinunf.result.Result
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.junit.Test

class ResultUtilsTests {
    @Test
    fun `test isSuccessful() works correctly`() {
        var result = Result.of { 1 }
        result.isSuccessful().`should be true`()

        result = Result.error(Exception())
        result.isSuccessful().`should be false`()
    }
}