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

package com.example.makeitso

import com.example.makeitso.model.Task
import com.example.makeitso.model.service.StorageService
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AwesomeTest {

  @get:Rule val hilt = HiltAndroidRule(this)

  @Inject lateinit var storage: StorageService
  @Inject lateinit var firestore: FirebaseFirestore

  @Before
  fun setup() {
    hilt.inject()
    runBlocking { firestore.clearPersistence().await() }
  }

  @Test
  fun test() = runTest {
    val newId = storage.save(Task(title = "Testing"))
    val result = storage.tasks.first()
    assertThat(result).containsExactly(Task(id = newId, title = "Testing"))
  }
}
