package com.github.amatkivskiy.template.presentation.ui

import activitystarter.ActivityStarter
import android.app.Activity
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.amatkivskiy.template.presentation.App
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import javax.inject.Inject

abstract class BaseDaggerMvpFragment<View : MvpView, Presenter : MvpPresenter<View>> : Fragment(), MvpView, MvpDelegateCallback<View, Presenter> {
    private val mvpDelegate: FragmentMvpDelegate<*, *> by lazy {
        FragmentMvpDelegateImpl(this, this, true, true)
    }

    @Inject
    lateinit var internalPresenter: Presenter

    abstract fun createPresenter(presenterComponent: PresenterComponent): Presenter

    abstract val viewResourceId: Int

    override fun getPresenter(): Presenter = internalPresenter

    override fun setPresenter(presenter: Presenter) {
        this.internalPresenter = presenter
    }

    @Suppress("UNCHECKED_CAST")
    override fun getMvpView(): View {
        return this as View
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val rootView = inflater.inflate(viewResourceId, container, false)
        ActivityStarter.fill(this, savedInstanceState)
        return rootView
    }

    override fun onViewCreated(fragmentView: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(fragmentView, savedInstanceState)
        mvpDelegate.onViewCreated(fragmentView, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityStarter.fill(this, savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate.onActivityCreated(savedInstanceState)
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mvpDelegate.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     * [.setRetainInstance] is set to true. This method will be called from
     * [.onViewCreated]
     */
    override fun createPresenter(): Presenter {
        return createPresenter(getPresenterComponent())
    }

    private fun getPresenterComponent(): PresenterComponent = App.getPresenterComponent(requireContext())
}
