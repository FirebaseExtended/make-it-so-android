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

package com.example.makeitso.model.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AccountService {
    fun hasUser(): Boolean
    fun isAnonymousUser(): Boolean
    fun getUserId(): String
    fun getAnonymousUserId(): String
    fun authenticate(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun createAccount(email: String, password: String, callback: (Task<AuthResult>) -> Unit)
    fun sendRecoveryEmail(email: String, callback: (Throwable?) -> Unit)
    fun createAnonymousAccount(callback: (Task<AuthResult>) -> Unit)
    fun linkAccount(email: String, password: String, callback: () -> Unit)
    fun deleteAccount(callback: (Throwable?) -> Unit)
    fun signOut()
}
