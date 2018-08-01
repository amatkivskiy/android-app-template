package com.github.amatkivskiy.template.presentation.ui.details

import activitystarter.ActivityStarter
import activitystarter.Arg
import activitystarter.MakeActivityStarter
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.github.amatkivskiy.template.R
import com.github.amatkivskiy.template.domain.model.User
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.github.amatkivskiy.template.presentation.ui.BaseDaggerMvpActivity
import com.github.amatkivskiy.template.presentation.ui.details.contacts.UserContactsFragmentStarter
import com.github.amatkivskiy.template.presentation.ui.details.photos.UserPhotosFragment
import com.github.amatkivskiy.template.util.CircleTransform
import com.github.amatkivskiy.template.util.loadUrl
import com.marcinmoskala.activitystarter.argExtra
import com.mcxiaoke.koi.ext.toast
import kotterknife.bindView

@MakeActivityStarter
class UserDetailsActivity : BaseDaggerMvpActivity<UserDetailsView, UserDetailsPresenter>(), UserDetailsView {
    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private val userFullNameTextView: TextView by bindView(R.id.textview_user_full_name)
    private val userAvatarImageView: ImageView by bindView(R.id.image_user_avatar)

    private val userSectionsTabLayout: TabLayout by bindView(R.id.tablyout_user_sections)
    private val userSectionsViewPager: ViewPager by bindView(R.id.viewpager_user_sections)

    @get:Arg
    var userId: String by argExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityStarter.fill(this, savedInstanceState)

        setContentView(R.layout.activity_user_details)

        setSupportActionBar(toolbar)
    }

    override fun createPresenter(presenterComponent: PresenterComponent) = presenterComponent.userDetailsPresenter()

    override fun renderUserDetails(user: User) {
        userFullNameTextView.text = "${user.name.first} ${user.name.last}"

        userAvatarImageView.loadUrl(url = user.picture.large, transformations = listOf(CircleTransform()))

        val adapterViewPager = UserSectionsPagerAdapter(supportFragmentManager, user)
        userSectionsViewPager.adapter = adapterViewPager

        userSectionsTabLayout.setupWithViewPager(userSectionsViewPager)
    }

    override fun userId() = userId

    override fun showError(message: String) {
        toast(message)
    }

    class UserSectionsPagerAdapter(fm: FragmentManager?, val user: User) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return UserPhotosFragment()
                1 -> return UserContactsFragmentStarter.newInstance(user.email, user.phone, user.cell)
            }

            TODO("Should not get here")
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Photos"
                1 -> return "Contacts"
            }

            TODO("Should not get here")
        }
    }
}
