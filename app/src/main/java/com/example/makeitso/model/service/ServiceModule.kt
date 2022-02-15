package com.example.makeitso.model.service

import com.example.makeitso.model.service.impl.AccountServiceImpl
import com.example.makeitso.model.service.impl.CrashlyticsServiceImpl
import com.example.makeitso.model.service.impl.FirestoreServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideCrashlyticsService(impl: CrashlyticsServiceImpl): CrashlyticsService

    @Binds
    abstract fun provideFirestoreService(impl: FirestoreServiceImpl): FirestoreService
}