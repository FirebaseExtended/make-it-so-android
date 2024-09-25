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

package com.example.makeitso.common.ext

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.textButton(): Modifier {
  return this.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp)
}

fun Modifier.basicButton(): Modifier {
  return this.fillMaxWidth().padding(16.dp, 8.dp)
}

fun Modifier.card(): Modifier {
  return this.padding(16.dp, 0.dp, 16.dp, 8.dp)
}

fun Modifier.contextMenu(): Modifier {
  return this.wrapContentWidth()
}

fun Modifier.alertDialog(): Modifier {
  return this.wrapContentWidth().wrapContentHeight()
}

fun Modifier.dropdownSelector(): Modifier {
  return this.fillMaxWidth()
}

fun Modifier.fieldModifier(): Modifier {
  return this.fillMaxWidth().padding(16.dp, 4.dp)
}

fun Modifier.toolbarActions(): Modifier {
  return this.wrapContentSize(Alignment.TopEnd)
}

fun Modifier.spacer(): Modifier {
  return this.fillMaxWidth().padding(12.dp)
}

fun Modifier.smallSpacer(): Modifier {
  return this.fillMaxWidth().height(8.dp)
}
