package com.github.amatkivskiy.template.presentation.dagger.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.amatkivskiy.template.presentation.App
import com.github.amatkivskiy.template.presentation.executors.JobExecutor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun preferences(application: App): SharedPreferences {
        return application.getSharedPreferences("test", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun context(application: App): Context = application

    @Provides
    @Singleton
    fun threadExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @Provides
    @Singleton
    fun postExecutionThread(): PostExecutionThread {
        return object : PostExecutionThread {
            override val scheduler: Scheduler
                get() = AndroidSchedulers.mainThread()
        }
    }

    @Provides
    @Singleton
    fun gson(): Gson = Gson()
}