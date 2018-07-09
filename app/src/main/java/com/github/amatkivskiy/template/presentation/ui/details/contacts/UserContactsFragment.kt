package com.github.amatkivskiy.template.presentation.ui.details.contacts

import activitystarter.ActivityStarterConfig
import activitystarter.Arg
import android.widget.TextView
import com.github.amatkivskiy.template.R
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.github.amatkivskiy.template.presentation.ui.BaseDaggerMvpFragment
import com.github.amatkivskiy.template.presentation.utils.bindView
import com.marcinmoskala.activitystarter.argExtra

@ActivityStarterConfig
class UserContactsFragment : BaseDaggerMvpFragment<UserContactsView, UserContactsPresenter>(), UserContactsView {
    override val viewResourceId: Int
        get() = R.layout.fragment_user_contacts

    private val userEmailTextView: TextView by bindView(R.id.textview_user_email)
    private val userPhoneTextView: TextView by bindView(R.id.textview_user_phone)
    private val userCellTextView: TextView by bindView(R.id.textview_user_cell)

    @get:Arg
    override var userEmail: String by argExtra()
    @get:Arg
    override val userPhone: String by argExtra()
    @get:Arg
    override val userCell: String by argExtra()

    override fun renderUserEmail(email: String) {
        userEmailTextView.text = email
    }

    override fun renderUserPhone(phone: String) {
        userPhoneTextView.text = phone
    }

    override fun renderUserCell(cell: String) {
        userCellTextView.text = cell
    }

    override fun createPresenter(presenterComponent: PresenterComponent) = presenterComponent.userContactsPresenter()
}