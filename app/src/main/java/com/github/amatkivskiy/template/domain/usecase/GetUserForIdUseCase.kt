package com.github.amatkivskiy.template.domain.usecase

import arrow.core.Option
import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.amatkivskiy.template.domain.datasource.UserDataSource
import com.github.amatkivskiy.template.domain.model.User
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetUserForIdUseCase @Inject constructor(
    threadExecutor: ThreadExecutor?,
    postExecutionThread: PostExecutionThread?,
    private val userDataSource: UserDataSource
) : UseCase<Option<User>, Exception>(threadExecutor, postExecutionThread) {
    private lateinit var userEmail: String

    fun forUserEmail(userEmail: String): GetUserForIdUseCase {
        this.userEmail = userEmail
        return this
    }

    override fun getRawObservable(): Observable<Result<Option<User>, Exception>> {
        return userDataSource.getUserForEmail(userEmail)
    }
}