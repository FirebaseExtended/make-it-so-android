package com.google.firebase.example.makeitso

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.example.makeitso.ui.home.HomeRoute
import com.google.firebase.example.makeitso.ui.home.HomeScreen
import com.google.firebase.example.makeitso.ui.signin.SignInRoute
import com.google.firebase.example.makeitso.ui.signin.SignInScreen
import com.google.firebase.example.makeitso.ui.signup.SignUpRoute
import com.google.firebase.example.makeitso.ui.signup.SignUpScreen
import com.google.firebase.example.makeitso.ui.theme.MakeItSoTheme
import com.google.firebase.example.makeitso.ui.todoitem.TodoItemRoute
import com.google.firebase.example.makeitso.ui.todoitem.TodoItemScreen
import com.google.firebase.example.makeitso.ui.todolist.TodoListRoute
import com.google.firebase.example.makeitso.ui.todolist.TodoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setSoftInputMode()

        setContent {
            val navController = rememberNavController()

            MakeItSoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = HomeRoute,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<HomeRoute> { HomeScreen(
                                openSignInScreen = {
                                    navController.navigate(SignInRoute) { launchSingleTop = true }
                                }
                            ) }
                            composable<SignInRoute> { SignInScreen() }
                            composable<SignUpRoute> { SignUpScreen() }
                            composable<TodoItemRoute> { TodoItemScreen() }
                            composable<TodoListRoute> { TodoListScreen() }
                        }
                    }
                }
            }
        }
    }

    private fun setSoftInputMode() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
}