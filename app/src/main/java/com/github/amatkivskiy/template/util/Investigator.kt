package com.github.amatkivskiy.template.util

import timber.log.Timber

/**
 * Logs the calling instance and method name.
 *
 *
 * **Example**
 * <br></br>Code:
 * <br></br>`Investigator.log(this);`
 * <br></br>Log:
 * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate()`
 *
 * @param instance the calling object instance
 */
fun log(instance: Any) {
    Investigator.doLog(instance, null, null, Investigator.defaultMethodDepth)
}

/**
 * Logs the calling instance and method name, and the comment.
 *
 *
 * **Example**
 * <br></br>Code:
 * <br></br>`Investigator.log(this, "some comment");`
 * <br></br>Log:
 * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | some comment`
 *
 * @param instance the calling object instance
 * @param comment extra comment message
 */
fun log(instance: Any, comment: String) {
    Investigator.doLog(instance, comment, null, Investigator.defaultMethodDepth)
}

/**
 * Logs the calling instance and method name, and the variable names and values.
 *
 *
 * **Example**
 * <br></br>Code:
 * <br></br>`Investigator.log(this, "fruit", fruit);`
 * <br></br>`Investigator.log(this, "fruit", fruit, "color", color);`
 * <br></br>Log:
 * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | fruit = cherry`
 * <br></br>`D/Investigator: MainActivity@788dc5c.onCreate() | fruit = cherry | color = red`
 *
 * @param instance the calling object instance
 * @param variableNamesAndValues variable name and value pairs
 */
fun log(instance: Any, vararg variableNamesAndValues: Any) {
    Investigator.doLog(instance, null, variableNamesAndValues, Investigator.defaultMethodDepth)
}

/**
 * Logs the calling instance and method name, and the stacktrace to the given method depth.
 *
 *
 * **Example**
 * <br></br>Code:
 * <br></br>`Investigator.log(this, 3);`
 * <br></br>Log:
 * <br></br>`D/Investigator: [main] MainActivity@cea8175.stackTrace()<br></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;at gk.android.investigator.sample.MainActivity.access$500(MainActivity.java:20)<br></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;at gk.android.investigator.sample.MainActivity$7.onClick(MainActivity.java:87)<br></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;at android.view.View.performClick(View.java:5204)`
 *
 * @param instance the calling object instance
 * @param methodDepth the number of methods to log from the stacktrace
 */
fun log(instance: Any, methodDepth: Int) {
    Investigator.doLog(instance, null, null, methodDepth)
}

class Investigator {
    companion object {
        /**
         * Number of the extra stacktrace elements (class + method name) logged from the stacktrace created at the log() call.
         * Zero means no extra method is logged, only the watched one.
         *
         *
         */
        const val defaultMethodDepth = 0
        /**
         * Log the thread name or not.
         */
        private const val threadNameEnabled = true

        /**
         * Remove the package name from the instance's toString value for easier readability.
         */
        private const val removePackageName = true
        /**
         * When enabled, an extra word ([.anonymousClassHighlightWord]) is inserted into anonymous classes' toString values to help notice them more easily.
         *
         * e.g.: `FirstFragment$1@1bf1abe3.onClick()` --&gt; `FirstFragment_INNNER_1@1bf1abe3.onClick()`
         */
        private const val highlightAnonymousClasses = true
        private const val anonymousClassHighlightWord = "_ANONYMOUS_"

        private const val patternThreadName = "[%s] "
        private const val patternInstanceAndMethod = "%s.%s()"
        private const val patternComment = " | %s"
        private const val patternVariableNameAndValue = " | %s = %s"
        private const val messageStopwatchStarted = " | 0 ms (STOPWATCH STARTED)"
        private const val patternElapsedTime = " | %s ms"
        private const val patternStacktraceLine = "\tat %s"
        private const val newLine = "\n"

        private const val STACKTRACE_INDEX_OF_CALLING_METHOD = 3 // fixed value, need to update only if the 'location' of the stack trace fetching code changes
        private const val ANONYMOUS_CLASS_TO_STRING_SYMBOL = "$"

        internal fun doLog(instance: Any, comment: String?, variableNamesAndValues: Array<out Any>?, methodDepth: Int) {
            val stackTrace = getStackTrace()
            val msg = StringBuilder()

            if (Investigator.threadNameEnabled) {
                msg.append(threadName())
            }

            msg.append(instanceAndMethodName(instance, stackTrace))

            if (comment != null) {
                msg.append(commentMessage(comment))
            }
            if (variableNamesAndValues != null) {
                msg.append(variablesMessage(*variableNamesAndValues))
            }
            if (methodDepth > 0) {
                msg.append(extraStackTraceLines(stackTrace, methodDepth))
            }
            logText(msg)
        }

        private fun getStackTrace(): Array<StackTraceElement> {
            // new Exception().getStackTrace() is faster than Thread.currentThread().getStackTrace(), see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6375302
            return Exception().stackTrace
        }

        private fun threadName(): String {
            return String.format(Investigator.patternThreadName, Thread.currentThread().name)
        }

        private fun instanceAndMethodName(instance: Any, stackTrace: Array<StackTraceElement>): String {
            val methodName = stackTrace[Investigator.STACKTRACE_INDEX_OF_CALLING_METHOD].methodName
            var instanceName = instance.toString()
            if (Investigator.removePackageName) {
                instanceName = removePackageName(instanceName)
            }
            instanceName = checkAndHighlightAnonymousClass(instanceName)
            return String.format(Investigator.patternInstanceAndMethod, instanceName, methodName)
        }

        // VisibleForTesting
        private fun removePackageName(instanceName: String): String {
            val lastDotIndex = instanceName.lastIndexOf(".")
            return if (lastDotIndex < 0 || lastDotIndex == instanceName.length - 1) {
                instanceName
            } else {
                instanceName.substring(lastDotIndex + 1)
            }
        }

        // VisibleForTesting
        private fun checkAndHighlightAnonymousClass(instanceName: String): String {
            if (!Investigator.highlightAnonymousClasses) {
                return instanceName
            }
            val symbolIndex = instanceName.indexOf(Investigator.ANONYMOUS_CLASS_TO_STRING_SYMBOL)
            val hasSymbolPlusDigit = symbolIndex > 0 && instanceName.length > symbolIndex + 1 && Character.isDigit(instanceName[symbolIndex + 1])
            return if (hasSymbolPlusDigit) {
                StringBuilder(instanceName).deleteCharAt(symbolIndex).insert(symbolIndex, Investigator.anonymousClassHighlightWord).toString()
            } else {
                instanceName
            }
        }

        private fun commentMessage(comment: String): String {
            return String.format(Investigator.patternComment, comment)
        }

        private fun variablesMessage(vararg variableNamesAndValues: Any): StringBuilder {
            try {
                val result = StringBuilder()
                var i = 0
                while (i < variableNamesAndValues.size) {
                    val variableName = variableNamesAndValues[i]
                    val variableValue = variableNamesAndValues[++i] // Will fail on odd number of params deliberately
                    val variableMessage = String.format(Investigator.patternVariableNameAndValue, variableName, variableValue)
                    result.append(variableMessage)
                    i++
                }
                return result
            } catch (e: ArrayIndexOutOfBoundsException) {
                throw IllegalArgumentException("Missed to add variable names and values in pairs? There has to be an even number of the 'variableNamesAndValues' varargs parameters).", e)
            }
        }

        private fun extraStackTraceLines(stackTrace: Array<StackTraceElement>, methodDepth: Int?): StringBuilder {
            val extraLines = StringBuilder()
            var i = Investigator.STACKTRACE_INDEX_OF_CALLING_METHOD + 1
            while (i <= Investigator.STACKTRACE_INDEX_OF_CALLING_METHOD + methodDepth!! && i < stackTrace.size) {
                extraLines.append(Investigator.newLine).append(stackTraceLine(stackTrace[i]))
                i++
            }
            return extraLines
        }

        private fun stackTraceLine(stackTraceElement: StackTraceElement): String {
            return String.format(Investigator.patternStacktraceLine, stackTraceElement.toString())
        }

        private fun logText(message: StringBuilder) {
            Timber.d(message.toString())
        }
    }
}