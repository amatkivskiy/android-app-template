package com.github.amatkivskiy.template.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

public inline fun <reified T> Gson.fromJson(reader: Reader): T = this.fromJson<T>(reader, object :
        TypeToken<T>() {}.type)