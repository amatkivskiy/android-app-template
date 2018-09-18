package com.github.amatkivskiy.template.utils

import com.github.amatkivskiy.template.util.Investigator
import com.github.amatkivskiy.template.util.InvestigatorLogger
import com.github.amatkivskiy.template.util.log
import org.amshove.kluent.`should match`
import org.junit.Before
import org.junit.Test

class InvestigatorTests {
    private val testLogger: TestLogger = TestLogger()

    @Before
    fun setUp() {
        Investigator.customLogger = testLogger
    }

    @Test
    fun `check Investigator logs correct message for instance`() {
        testLogger.setNewMessageToTest("\\[[a-zA-Z0-9\\s]*\\] InvestigatorTests@[a-z0-9]{6,}.check" +
                " Investigator logs correct message for instance\\(\\)")

        log(this)
    }

    @Test
    fun `check Investigator logs correct message for instance and comment`() {

        testLogger.setNewMessageToTest("\\[[a-zA-Z0-9\\s]*\\] InvestigatorTests@[a-z0-9]{6,}.check Investigator logs correct message for instance and comment\\(\\) \\| comment")

        log(this, "comment")
    }

    @Test
    fun `check Investigator logs correct message for instance and supplied variables`() {
        testLogger.setNewMessageToTest("\\[[a-zA-Z0-9\\s]*\\] InvestigatorTests@[a-z0-9]{6,}." +
                "check Investigator logs correct message for instance and supplied variables\\" +
                "(\\) \\| variable1 = string \\| variable2 = 1")

        val variable1 = "string"
        val variable2 = 1

        log(this, "variable1", variable1, "variable2", variable2)
    }

    @Test
    fun `check Investigator logs class with custom toString() correctly`() {
        testLogger.setNewMessageToTest("\\[[a-zA-Z0-9\\s]*\\] " +
                "\\(1, 2\\).check Investigator logs class with custom toString\\(\\) correctly\\(\\)")

        log(Pair(1, 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `check Investigator throws exception when wrong number of variables is provided`() {
        log(this, "variable1", "value1", "variable2")
    }

    @Test(expected = IllegalStateException::class)
    fun `check Investigator throws exception when custom logger is not provided`() {
        Investigator.customLogger = null

        log(this, "comment")
    }
}

class TestLogger : InvestigatorLogger {
    private var messageToTest = ""

    fun setNewMessageToTest(newMessage: String) {
        messageToTest = newMessage
    }

    override fun logMessage(message: String) {
        message.`should match`(messageToTest)
    }
}