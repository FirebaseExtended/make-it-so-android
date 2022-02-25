package com.example.makeitso.model.database.repository

import com.example.makeitso.model.database.repository.impl.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}