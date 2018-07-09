package com.github.amatkivskiy.template.presentation.dagger.modules

import android.content.Context
import com.github.amatkivskiy.template.data.datasource.DefaultUserDataSource
import com.github.amatkivskiy.template.data.repository.AssertsReader
import com.github.amatkivskiy.template.data.repository.ContextAssertsReader
import com.github.amatkivskiy.template.data.repository.DiskUserRepository
import com.github.amatkivskiy.template.domain.datasource.UserDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {
    @Provides
    @Singleton
    fun assertsReader(context: Context): AssertsReader = ContextAssertsReader(context)

    @Provides
    @Singleton
    fun userDataSource(assertsReader: AssertsReader, gson: Gson): UserDataSource =
        DefaultUserDataSource(DiskUserRepository(assertsReader, gson))
}