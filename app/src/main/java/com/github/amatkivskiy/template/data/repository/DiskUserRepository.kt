package com.github.amatkivskiy.template.data.repository

import arrow.core.Option
import com.github.amatkivskiy.template.domain.model.User
import com.github.amatkivskiy.template.util.fromJson
import com.google.gson.Gson
import io.reactivex.Observable
import java.io.InputStreamReader
import javax.inject.Inject

class DiskUserRepository @Inject constructor(private val reader: AssertsReader, private val gson: Gson) {

    fun getUserList(): Observable<List<User>> {
        return Observable.fromCallable {
            gson.fromJson<List<User>>(InputStreamReader(reader.readDataFromAssert("users-list.json")))
        }
    }

    fun getUserForEmail(email: String): Observable<Option<User>> {
        return getUserList()
            .map { it ->
                val user = it.find { email == it.email }

                return@map Option.fromNullable(user)
            }
    }
}