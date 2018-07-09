package com.github.amatkivskiy.template.presentation.ui.details.photos

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import timber.log.Timber
import java.util.Random
import javax.inject.Inject

class UserPhotosPresenter @Inject constructor() : @Inject MvpNullObjectBasePresenter<UserPhotosView>() {
    override fun attachView(view: UserPhotosView) {
        super.attachView(view)

        val random = Random()

        val bottomBorder = 0
        val upperBorder = 1000
        val pictureWidth = 500

        val photoUrls = 1.rangeTo(3)
            .map {
                // Just generate random number from [bottomBorder:upperBorder]
                random.nextInt(upperBorder - bottomBorder) + bottomBorder
            }.map {
                // And then create an URL like https://picsum.photos/500?image=220 that may sometimes fail
                "https://picsum.photos/$pictureWidth?image=$it"
            }.map {
                // Just print into the log
                Timber.d("Generated URL: $it")
                it
            }

        view.renderUserPhotos(photoUrls)
    }
}
