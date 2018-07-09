package com.github.amatkivskiy.template.presentation.ui.main

import com.github.amatkivskiy.template.domain.model.User
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MainView : MvpView {
    fun renderUsers(beers: List<User>)
    fun navigateToUserDetails(userId: String)
}