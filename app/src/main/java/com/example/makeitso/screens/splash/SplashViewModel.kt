/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.CrashlyticsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val crashlyticsService: CrashlyticsService
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch { crashlyticsService.logNonFatalCrash(throwable) }
    }

    fun onAppStart(openTasks: () -> Unit) {
        if (accountService.hasUser()) openTasks()
        else createAnonymousAccount(openTasks)
    }

    private fun createAnonymousAccount(openTasks: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            accountService.createAnonymousAccount { task ->
                if (task.isSuccessful) openTasks()
                else crashlyticsService.logNonFatalCrash(task.exception)
            }
        }
    }
}