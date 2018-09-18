package com.github.amatkivskiy.template.util

fun <T : Any> T?.whenNotNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

fun <T : Any> T?.whenNull(f: () -> Unit) {
    if (this == null) f()
}