package com.example.makeitso

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.NavHost
import com.example.makeitso.theme.MakeItSoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MakeItSoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //If there is an user logged in (check shared prefs) -> TasksScreen
        //Else -> LoginScreen
        val firstScreen = LOGIN_SCREEN

        setContent {
            MakeItSoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(firstScreen = firstScreen)
                }
            }
        }
    }
}
