package com.github.amatkivskiy.template.presentation.ui.details.photos

import android.widget.ImageView
import android.widget.LinearLayout
import com.github.amatkivskiy.template.R
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.github.amatkivskiy.template.presentation.ui.BaseDaggerMvpFragment
import com.github.amatkivskiy.template.util.loadUrl
import kotterknife.bindView

class UserPhotosFragment : BaseDaggerMvpFragment<UserPhotosView, UserPhotosPresenter>(), UserPhotosView {
    private val photosContainerLayout: LinearLayout by bindView(R.id.layout_photos_container)

    override val viewResourceId: Int
        get() = R.layout.fragment_user_photos

    override fun createPresenter(presenterComponent: PresenterComponent) = presenterComponent.userPhotosPresenter()

    override fun renderUserPhotos(photoUrls: List<String>) {
        photoUrls.forEach {
            val photoView = layoutInflater.inflate(R.layout.item_user_photo, photosContainerLayout, false)
                .findViewById<ImageView>(R.id.image_user_photo)

            photosContainerLayout.addView(photoView)

            photoView.loadUrl(it, R.mipmap.ic_launcher)
        }
    }
}