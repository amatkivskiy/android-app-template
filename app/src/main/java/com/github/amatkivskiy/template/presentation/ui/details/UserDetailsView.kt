package com.github.amatkivskiy.template.presentation.ui.details

import com.github.amatkivskiy.template.domain.model.User
import com.hannesdorfmann.mosby3.mvp.MvpView

interface UserDetailsView : MvpView {
    fun renderUserDetails(user: User)
    fun userId(): String
    fun showError(message: String)
}