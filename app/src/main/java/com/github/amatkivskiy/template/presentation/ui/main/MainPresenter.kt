package com.github.amatkivskiy.template.presentation.ui.main

import com.github.amatkivskiy.template.domain.model.User
import com.github.amatkivskiy.template.domain.usecase.GetUsersListUseCase
import com.github.kittinunf.result.success
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(private val getUsersLis: GetUsersListUseCase) : @Inject MvpNullObjectBasePresenter<MainView>() {

    override fun attachView(view: MainView) {
        super.attachView(view)

        getUsersLis.getConfiguredObservable().subscribe {
            it.success {
                view.renderUsers(it)
            }
        }
    }

    fun onUserClick(user: User) {
        view.navigateToUserDetails(user.email)
    }
}