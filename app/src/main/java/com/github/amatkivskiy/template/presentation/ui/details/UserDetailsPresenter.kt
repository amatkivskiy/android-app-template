package com.github.amatkivskiy.template.presentation.ui.details

import arrow.core.None
import arrow.core.Some
import com.github.amatkivskiy.template.domain.usecase.GetUserForIdUseCase
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

class UserDetailsPresenter @Inject constructor(private val getUserForIdUseCase: GetUserForIdUseCase) : @Inject MvpNullObjectBasePresenter<UserDetailsView>() {
    override fun attachView(view: UserDetailsView) {
        super.attachView(view)

        getUserForIdUseCase.forUserEmail(view.userId()).getConfiguredObservable().subscribe {
            it.success {
                when (it) {
                    is Some -> view.renderUserDetails(it.t)
                    is None -> view.showError("User not found.")
                }
            }

            it.failure {
                view.showError("User not found.")
            }
        }
    }
}