package com.github.amatkivskiy.template.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.amatkivskiy.template.R
import com.github.amatkivskiy.template.domain.model.User
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.github.amatkivskiy.template.presentation.ui.BaseDaggerMvpActivity
import com.github.amatkivskiy.template.presentation.ui.details.UserDetailsActivityStarter
import com.github.amatkivskiy.template.util.CircleTransform
import com.github.amatkivskiy.template.util.loadUrl
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter
import com.marshalchen.ultimaterecyclerview.ItemTouchListenerAdapter.RecyclerViewOnItemClickListener
import com.marshalchen.ultimaterecyclerview.UltimateGridLayoutAdapter
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder
import com.marshalchen.ultimaterecyclerview.grid.BasicGridLayoutManager
import com.mcxiaoke.koi.ext.toast
import kotterknife.bindView

class MainActivity : BaseDaggerMvpActivity<MainView, MainPresenter>(), MainView {
    private val usersListRecyclerView: UltimateRecyclerView by bindView(R.id.recycler_view_beers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun renderUsers(beers: List<User>) {
        val usersGridAdapter = UsersGridAdapter(beers.toMutableList())

        usersListRecyclerView.layoutManager = BasicGridLayoutManager(this, 2, usersGridAdapter)
        usersListRecyclerView.setAdapter(usersGridAdapter)
        usersListRecyclerView.setHasFixedSize(true)

        val clickListener = ItemTouchListenerAdapter(usersListRecyclerView.mRecyclerView, object : RecyclerViewOnItemClickListener {
            override fun onItemLongClick(parent: RecyclerView?, clickedView: View?, position: Int) {
                toast("Not supported.")
            }

            override fun onItemClick(parent: RecyclerView?, clickedView: View?, position: Int) {
                presenter.onUserClick(usersGridAdapter.getItem(position))
            }
        })
        usersListRecyclerView.addOnItemTouchListener(clickListener)

        usersListRecyclerView.isSaveEnabled = true
        usersListRecyclerView.clipToPadding = false
    }

    override fun createPresenter(presenterComponent: PresenterComponent) = getPresenterComponent().mainPresenter()

    override fun navigateToUserDetails(userId: String) {
        UserDetailsActivityStarter.start(this, userId)
    }

    class UsersGridAdapter(items: MutableList<User>?) : UltimateGridLayoutAdapter<User, UserItemViewHolder>(items) {
        override fun withBindHolder(holder: UserItemViewHolder?, data: User?, position: Int) {
        }

        override fun bindNormal(holder: UserItemViewHolder?, item: User?, position: Int) {
            holder?.onBindView(item)
        }

        override fun newViewHolder(view: View?): UserItemViewHolder = UserItemViewHolder(view)

        override fun getNormalLayoutResId(): Int = R.layout.item_user_item

        public override fun getItem(pos: Int): User {
            return super.getItem(pos)
        }
    }

    class UserItemViewHolder(itemView: View?) : UltimateRecyclerviewViewHolder<User>(itemView) {
        private val userNameTextView: TextView by bindView(R.id.textview_user_name)
        private val userAvatarImageView: ImageView by bindView(R.id.image_user_avatar)

        override fun updateView(context: Context?, item: User?) {
            val fullUserName = "${item?.name?.first} ${item?.name?.last}"
            userNameTextView.text = fullUserName

            userAvatarImageView.loadUrl(url = item?.picture?.large, transformations = listOf(CircleTransform()))
        }
    }
}
