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

package com.example.makeitso.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.*
import com.example.makeitso.common.ext.card
import com.example.makeitso.common.ext.spacer
import com.example.makeitso.theme.MakeItSoTheme

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
  restartApp: (String) -> Unit,
  openScreen: (String) -> Unit,
  viewModel: SettingsViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

  SettingsScreenContent(
    uiState = uiState,
    onLoginClick = { viewModel.onLoginClick(openScreen) },
    onSignUpClick = { viewModel.onSignUpClick(openScreen) },
    onSignOutClick = { viewModel.onSignOutClick(restartApp) },
    onDeleteMyAccountClick = { viewModel.onDeleteMyAccountClick(restartApp) }
  )
}

@ExperimentalMaterialApi
@Composable
fun SettingsScreenContent(
  modifier: Modifier = Modifier,
  uiState: SettingsUiState,
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onSignOutClick: () -> Unit,
  onDeleteMyAccountClick: () -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    BasicToolbar(AppText.settings)

    Spacer(modifier = Modifier.spacer())

    if (uiState.isAnonymousAccount) {
      RegularCardEditor(AppText.sign_in, AppIcon.ic_sign_in, "", Modifier.card()) {
        onLoginClick()
      }

      RegularCardEditor(AppText.create_account, AppIcon.ic_create_account, "", Modifier.card()) {
        onSignUpClick()
      }
    } else {
      SignOutCard { onSignOutClick() }
      DeleteMyAccountCard { onDeleteMyAccountClick() }
    }
  }
}

@ExperimentalMaterialApi
@Composable
private fun SignOutCard(signOut: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }

  RegularCardEditor(AppText.sign_out, AppIcon.ic_exit, "", Modifier.card()) {
    showWarningDialog = true
  }

  if (showWarningDialog) {
    AlertDialog(
      title = { Text(stringResource(AppText.sign_out_title)) },
      text = { Text(stringResource(AppText.sign_out_description)) },
      dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
      confirmButton = {
        DialogConfirmButton(AppText.sign_out) {
          signOut()
          showWarningDialog = false
        }
      },
      onDismissRequest = { showWarningDialog = false }
    )
  }
}

@ExperimentalMaterialApi
@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }

  DangerousCardEditor(
    AppText.delete_my_account,
    AppIcon.ic_delete_my_account,
    "",
    Modifier.card()
  ) {
    showWarningDialog = true
  }

  if (showWarningDialog) {
    AlertDialog(
      title = { Text(stringResource(AppText.delete_account_title)) },
      text = { Text(stringResource(AppText.delete_account_description)) },
      dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
      confirmButton = {
        DialogConfirmButton(AppText.delete_my_account) {
          deleteMyAccount()
          showWarningDialog = false
        }
      },
      onDismissRequest = { showWarningDialog = false }
    )
  }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun SettingsScreenPreview() {
  val uiState = SettingsUiState(isAnonymousAccount = false)

  MakeItSoTheme {
    SettingsScreenContent(
      uiState = uiState,
      onLoginClick = { },
      onSignUpClick = { },
      onSignOutClick = { },
      onDeleteMyAccountClick = { }
    )
  }
}
