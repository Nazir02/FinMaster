package com.encom.finmaster.core.di

import com.encom.finmaster.repository.sqlite.MainRepository
import com.encom.finmaster.repository.sqlite.MainRepositoryimpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


object ApplicationModule {

    val applicationModule: Module = module {
        factory<MainRepository> { MainRepositoryimpl(androidContext()) }
    }

}
