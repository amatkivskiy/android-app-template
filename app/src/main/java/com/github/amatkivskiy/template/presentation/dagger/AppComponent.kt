package com.github.amatkivskiy.template.presentation.dagger

import android.content.SharedPreferences
import com.github.amatkivskiy.template.presentation.App
import com.github.amatkivskiy.template.presentation.dagger.component.PresenterComponent
import com.github.amatkivskiy.template.presentation.dagger.modules.ActivityBuilder
import com.github.amatkivskiy.template.presentation.dagger.modules.AppModule
import com.github.amatkivskiy.template.presentation.dagger.modules.DataSourceModule
import com.github.amatkivskiy.template.presentation.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilder::class, DataSourceModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun preferences(): SharedPreferences

    fun plusPresenterComponent(): PresenterComponent

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
}