package com.github.amatkivskiy.template.data.datasource

import arrow.core.Option
import com.github.amatkivskiy.template.data.repository.DiskUserRepository
import com.github.amatkivskiy.template.domain.datasource.UserDataSource
import com.github.amatkivskiy.template.domain.model.User
import com.github.amatkivskiy.template.util.toResult
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import javax.inject.Inject

class DefaultUserDataSource @Inject constructor(private val diskRepository: DiskUserRepository) : UserDataSource {
    override fun getUserForEmail(email: String): Observable<Result<Option<User>, Exception>> {
        return diskRepository.getUserForEmail(email).toResult()
    }

    override fun getUsersList(): Observable<Result<List<User>, Exception>> {
        return diskRepository.getUserList().toResult()
    }
}