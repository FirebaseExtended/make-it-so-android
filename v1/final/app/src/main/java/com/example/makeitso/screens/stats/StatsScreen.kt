/*
Copyright 2023 Google LLC

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

package com.example.makeitso.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.theme.MakeItSoTheme

@ExperimentalMaterialApi
@Composable
fun StatsScreen(
  viewModel: StatsViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  StatsScreenContent(uiState = uiState)
}

@Composable
fun StatsScreenContent(
  modifier: Modifier = Modifier,
  uiState: StatsUiState
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    BasicToolbar(AppText.stats)

    Spacer(modifier = Modifier.smallSpacer())

    StatsItem(titleRes = AppText.completed_tasks, value = uiState.completedTasksCount)
    StatsItem(titleRes = AppText.important_completed_tasks, value = uiState.importantCompletedTasksCount)
    StatsItem(titleRes = AppText.medium_high_tasks_to_complete, value = uiState.mediumHighTasksToCompleteCount)
  }
}

@Composable
fun StatsItem(titleRes: Int, value: Int) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp, vertical = 24.dp),
    ) {
      Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = stringResource(id = titleRes),
          style = MaterialTheme.typography.subtitle1,
          textAlign = TextAlign.Center
        )

        Text(text = "$value", fontSize = 48.sp)
      }
    }
  }

  Spacer(modifier = Modifier.smallSpacer())
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
  MakeItSoTheme {
    StatsScreenContent(
      uiState = StatsUiState()
    )
  }
}
