package com.github.amatkivskiy.template.presentation

import android.app.Activity
import android.app.Application
import android.content.Context
import com.github.amatkivskiy.template.BuildConfig
import com.github.amatkivskiy.template.presentation.dagger.AppComponent
import com.github.amatkivskiy.template.presentation.dagger.DaggerAppComponent
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    val rootComponent: AppComponent
        get() = DaggerAppComponent.builder().application(this).build()

    val presenterComponent: PresenterComponent
        get() = rootComponent.plusPresenterComponent()

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            FragmentMvpDelegateImpl.DEBUG = true
            ActivityMvpDelegateImpl.DEBUG = true

            Picasso.setSingletonInstance(Picasso.Builder(this)
                .loggingEnabled(true)
                .build())
        }

        rootComponent.inject(this)
    }

    companion object {
        fun getAppComponent(context: Context): AppComponent {
            val app = context.applicationContext as App
            return app.rootComponent
        }

        fun getPresenterComponent(context: Context): PresenterComponent {
            val app = context.applicationContext as App
            return app.presenterComponent
        }
    }
}