package com.example.makeitso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.makeitso.common.navigation.NavHost
import com.example.makeitso.common.navigation.SPLASH_SCREEN
import com.example.makeitso.theme.MakeItSoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MakeItSoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MakeItSoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(SPLASH_SCREEN)
                }
            }
        }
    }
}
