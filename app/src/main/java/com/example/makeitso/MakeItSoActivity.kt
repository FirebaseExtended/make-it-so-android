package com.example.makeitso

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.makeitso.common.navigation.LOGIN_SCREEN
import com.example.makeitso.common.navigation.NavHost
import com.example.makeitso.common.navigation.TASKS_SCREEN
import com.example.makeitso.theme.MakeItSoTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MakeItSoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAuth = Firebase.auth
        val firstScreen = if (firebaseAuth.currentUser != null) TASKS_SCREEN else LOGIN_SCREEN

        setContent {
            MakeItSoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(firstScreen, firebaseAuth)
                }
            }
        }
    }
}
