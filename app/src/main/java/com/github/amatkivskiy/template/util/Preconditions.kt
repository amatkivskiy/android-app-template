package com.github.amatkivskiy.template.util

fun <T : Any> checkNotNull(value: T?, valueName: String): T {
    return checkNotNull(value) {
        "'$valueName' should not be null"
    }
}