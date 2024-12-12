package com.google.firebase.example.makeitso.ui.todoitem

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.data.model.TodoItem
import com.google.firebase.example.makeitso.data.repository.AuthRepository
import com.google.firebase.example.makeitso.data.repository.TodoItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val todoItemRepository: TodoItemRepository
) : MainViewModel() {
    private val todoItemRoute = savedStateHandle.toRoute<TodoItemRoute>()
    private val itemId: String = todoItemRoute.itemId

    private val _todoItem = MutableStateFlow<TodoItem?>(null)
    val todoItem: StateFlow<TodoItem?>
        get() = _todoItem.asStateFlow()

    fun loadItem() {
        launchCatching {
            _todoItem.value = todoItemRepository.getTodoItem(itemId) ?: TodoItem()
        }
    }

    fun saveItem(
        item: TodoItem,
        openHomeScreen: () -> Unit,
        showErrorSnackbar: (String) -> Unit
    ) {
        val ownerId = authRepository.currentUser?.uid

        if (ownerId.isNullOrBlank()) {
            showErrorSnackbar(NO_ACCOUNT_ERROR)
            return
        }

        if (item.title.isBlank()) {
            showErrorSnackbar(NO_TITLE_ERROR)
            return
        }

        launchCatching {
            if (itemId.isBlank()) {
                todoItemRepository.create(item.copy(ownerId = ownerId))
            } else {
                todoItemRepository.update(item)
            }

            openHomeScreen()
        }
    }

    companion object {
        private const val NO_ACCOUNT_ERROR = "We couldn't find your account id at the moment, please try again later"
        private const val NO_TITLE_ERROR = "Title cannot be empty"
    }
}