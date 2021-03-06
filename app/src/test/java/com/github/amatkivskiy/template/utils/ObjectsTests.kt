package com.github.amatkivskiy.template.utils

import com.github.amatkivskiy.template.util.whenNotNull
import com.github.amatkivskiy.template.util.whenNull
import org.amshove.kluent.`should be true`
import org.junit.Test
import kotlin.test.fail

class ObjectsTests {
    @Test
    fun `whenNotNull() called on non null object`() {
        var functionCalled = false

        "non null value".whenNotNull {
            functionCalled = true
        }

        functionCalled.`should be true`()
    }

    @Test
    fun `whenNotNull() called on null object`() {
        null.whenNotNull {
            fail("Should not reach here")
        }
    }

    @Test
    fun `whenNull() called on null object`() {
        var functionNotCalled = false

        null.whenNull {
            functionNotCalled = true
        }

        functionNotCalled.`should be true`()
    }

    @Test
    fun `whenNull() called on non null object`() {
        "non null value".whenNull {
            fail("Should not reach here")
        }
    }
}