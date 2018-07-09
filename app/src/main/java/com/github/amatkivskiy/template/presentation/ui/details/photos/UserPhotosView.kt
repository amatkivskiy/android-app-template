package com.github.amatkivskiy.template.presentation.ui.details.photos

import com.hannesdorfmann.mosby3.mvp.MvpView

interface UserPhotosView : MvpView {
    fun renderUserPhotos(photoUrls: List<String>)
}