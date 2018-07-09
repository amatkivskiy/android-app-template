package com.github.amatkivskiy.template.presentation.dagger.component

import com.github.amatkivskiy.template.presentation.ui.details.UserDetailsPresenter
import com.github.amatkivskiy.template.presentation.ui.details.contacts.UserContactsPresenter
import com.github.amatkivskiy.template.presentation.ui.details.photos.UserPhotosPresenter
import com.github.amatkivskiy.template.presentation.ui.main.MainPresenter
import dagger.Subcomponent

@Subcomponent
interface PresenterComponent {
    fun mainPresenter(): MainPresenter
    fun userDetailsPresenter(): UserDetailsPresenter
    fun userPhotosPresenter(): UserPhotosPresenter
    fun userContactsPresenter(): UserContactsPresenter
}