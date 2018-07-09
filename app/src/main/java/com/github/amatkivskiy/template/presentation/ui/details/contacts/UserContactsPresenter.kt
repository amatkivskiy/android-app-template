package com.github.amatkivskiy.template.presentation.ui.details.contacts

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import javax.inject.Inject

class UserContactsPresenter @Inject constructor() : @Inject MvpNullObjectBasePresenter<UserContactsView>() {
    override fun attachView(view: UserContactsView) {
        super.attachView(view)

        view.renderUserEmail(view.userEmail)
        view.renderUserPhone(view.userPhone)
        view.renderUserCell(view.userCell)
    }
}