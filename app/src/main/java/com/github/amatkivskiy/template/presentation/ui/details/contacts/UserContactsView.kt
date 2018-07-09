package com.github.amatkivskiy.template.presentation.ui.details.contacts

import com.hannesdorfmann.mosby3.mvp.MvpView

interface UserContactsView : MvpView {
    fun renderUserEmail(email: String)
    fun renderUserPhone(phone: String)
    fun renderUserCell(cell: String)

    val userEmail: String
    val userPhone: String
    val userCell: String
}