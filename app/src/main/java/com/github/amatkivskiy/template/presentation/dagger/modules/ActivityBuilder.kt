package com.github.amatkivskiy.template.presentation.dagger.modules

import com.github.amatkivskiy.template.presentation.ui.details.UserDetailsActivity
import com.github.amatkivskiy.template.presentation.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindUserDetailsActivity(): UserDetailsActivity
}