package com.google.firebase.example.makeitso.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.ui.shared.LoadingIndicator
import com.google.firebase.example.makeitso.ui.shared.SIwGButton
import kotlinx.serialization.Serializable

@Serializable
object SignUpRoute

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value is SignUpUiState.Loading) {
        LoadingIndicator()
    } else {
        SignUpScreenContent(viewModel)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignUpScreenContent(viewModel: SignUpViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            val (appLogo, form) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(appLogo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

                Image(
                    modifier = Modifier.size(88.dp),
                    painter = painterResource(id = R.mipmap.ic_launcher_round),
                    contentDescription = "App logo"
                )

                Spacer(Modifier.size(24.dp))
            }

            Column(
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(appLogo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

                SIwGButton()
            }
        }
    }
}