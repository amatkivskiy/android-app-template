package com.github.amatkivskiy.template.data.repository

import arrow.core.Option
import com.github.amatkivskiy.template.domain.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject

class DiskUserRepository @Inject constructor(private val reader: AssertsReader, private val gson: Gson) {

    fun getUserList(): Observable<List<User>> {
        return Observable.fromCallable {
            gson.fromJson<List<User>>(InputStreamReader(reader.readDataFromAssert("users-list.json")))
        }
    }

    fun getUserForEmail(email: String): Observable<Option<User>> {
        return getUserList()
            .map {
                val user = it.find { email == it.email }

                return@map Option.fromNullable(user)
            }
    }

    private inline fun <reified T> Gson.fromJson(reader: Reader): T = this.fromJson<T>(reader, object : TypeToken<T>() {}.type)
}