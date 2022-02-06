package com.example.makeitso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.makeitso.screens.edit_task.EditTaskScreen
import com.example.makeitso.theme.MakeItSoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MakeItSoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FirstScreen()
                }
            }
        }
    }

    @Composable
    private fun FirstScreen() {
        //If there is an user logged in -> TasksScreen
        //Else -> LoginScreen
        EditTaskScreen()
    }
}
