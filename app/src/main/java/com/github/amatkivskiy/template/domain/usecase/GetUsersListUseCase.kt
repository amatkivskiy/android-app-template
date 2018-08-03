package com.github.amatkivskiy.template.domain.usecase

import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.amatkivskiy.template.domain.datasource.UserDataSource
import com.github.amatkivskiy.template.domain.model.User
import com.github.kittinunf.result.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val userDataSource: UserDataSource
) : UseCase<List<User>, Exception>(threadExecutor, postExecutionThread) {

    override fun getRawObservable(): Observable<Result<List<User>, Exception>> {
        return userDataSource.getUsersList()
    }
}