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

package com.example.makeitso.model.service.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [FirebaseModule::class])
object TestFirebaseModule {
  private const val HOST = "10.0.2.2"
  private const val AUTH_PORT = 9099
  private const val FIRESTORE_PORT = 8080
  @Provides fun auth(): FirebaseAuth = Firebase.auth.also { it.useEmulator(HOST, AUTH_PORT) }

  @Provides
  fun firestore(): FirebaseFirestore =
    Firebase.firestore.also { it.useEmulator(HOST, FIRESTORE_PORT) }
}
