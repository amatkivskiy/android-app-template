package com.github.amatkivskiy.template.presentation.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.amatkivskiy.template.presentation.App
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseDaggerMvpActivity<View : MvpView, Presenter : MvpNullObjectBasePresenter<View>> : AppCompatActivity(), MvpView, MvpDelegateCallback<View, Presenter> {

    private val mvpDelegate: ActivityMvpDelegate<*, *> by lazy {
        ActivityMvpDelegateImpl(this, this, true)
    }

    @Inject lateinit var internalPresenter: Presenter
    protected var retainInstance = false

    abstract fun createPresenter(presenterComponent: PresenterComponent): Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
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

    override fun onRestart() {
        super.onRestart()
        mvpDelegate.onRestart()
    }

    override fun onContentChanged() {
        super.onContentChanged()
        mvpDelegate.onContentChanged()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mvpDelegate.onPostCreate(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getMvpView(): View {
        return this as View
    }

    override fun createPresenter() = createPresenter(getPresenterComponent())

    override fun getPresenter(): Presenter = internalPresenter

    override fun setPresenter(presenter: Presenter) {
        this.internalPresenter = presenter
    }

    protected fun getPresenterComponent(): PresenterComponent = App.getPresenterComponent(this)
}