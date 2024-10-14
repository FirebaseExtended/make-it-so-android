package com.google.firebase.example.makeitso.ui.home

import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import com.google.firebase.example.makeitso.data.repository.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val todoListRepository: TodoListRepository
) : MainViewModel()