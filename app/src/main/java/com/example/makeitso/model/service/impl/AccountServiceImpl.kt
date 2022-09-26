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

package com.example.makeitso.model.service.impl

import com.example.makeitso.model.User
import com.example.makeitso.model.service.AccountService
import com.example.makeitso.model.service.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl @Inject constructor() : AccountService {
  override val currentUserId: String
    get() = Firebase.auth.currentUser?.uid.orEmpty()
  override val hasUser: Boolean
    get() = Firebase.auth.currentUser != null
  override val currentUser: Flow<User>
    get() = callbackFlow {
      val listener =
        FirebaseAuth.AuthStateListener { auth ->
          this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
        }
      Firebase.auth.addAuthStateListener(listener)
      awaitClose { Firebase.auth.removeAuthStateListener(listener) }
    }

  override suspend fun authenticate(email: String, password: String) {
    Firebase.auth.signInWithEmailAndPassword(email, password).await()
  }

  override suspend fun sendRecoveryEmail(email: String) {
    Firebase.auth.sendPasswordResetEmail(email).await()
  }

  override suspend fun createAnonymousAccount() {
    Firebase.auth.signInAnonymously().await()
  }

  override suspend fun linkAccount(email: String, password: String): Unit =
    trace(LINK_ACCOUNT_TRACE) {
      val credential = EmailAuthProvider.getCredential(email, password)

     Firebase.auth.currentUser!!.linkWithCredential(credential).await()
    }

  override suspend fun deleteAccount() {
    Firebase.auth.currentUser!!.delete().await()
  }

  override suspend fun signOut() {
    if (Firebase.auth.currentUser!!.isAnonymous) {
      Firebase.auth.currentUser!!.delete()
    }
    Firebase.auth.signOut()

    // Sign the user back in anonymously.
    createAnonymousAccount()
  }

  companion object {
    private const val LINK_ACCOUNT_TRACE = "linkAccount"
  }
}
