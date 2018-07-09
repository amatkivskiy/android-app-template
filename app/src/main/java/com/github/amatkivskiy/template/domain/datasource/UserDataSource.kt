package com.github.amatkivskiy.template.domain.datasource

import arrow.core.Option
import com.github.amatkivskiy.template.domain.model.User
import com.github.kittinunf.result.Result
import io.reactivex.Observable

interface UserDataSource {
    fun getUsersList(): Observable<Result<List<User>, Exception>>
    fun getUserForEmail(email: String): Observable<Result<Option<User>, Exception>>
}