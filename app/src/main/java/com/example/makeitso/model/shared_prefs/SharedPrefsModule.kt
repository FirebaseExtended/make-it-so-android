package com.example.makeitso.model.shared_prefs

import com.example.makeitso.model.shared_prefs.impl.SharedPrefsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SharedPrefsModule {
    @Binds
    abstract fun provideSharedPrefs(impl: SharedPrefsImpl): SharedPrefs
}