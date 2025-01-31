package com.google.firebase.example.makeitso.ui.todoitem

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.google.firebase.example.makeitso.MainViewModel
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.data.model.ErrorMessage
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
    private val _navigateToTodoList = MutableStateFlow(false)
    val navigateToTodoList: StateFlow<Boolean>
        get() = _navigateToTodoList.asStateFlow()

    private val todoItemRoute = savedStateHandle.toRoute<TodoItemRoute>()
    private val itemId: String = todoItemRoute.itemId

    private val _todoItem = MutableStateFlow<TodoItem?>(null)
    val todoItem: StateFlow<TodoItem?>
        get() = _todoItem.asStateFlow()

    fun loadItem() {
        launchCatching {
            if (itemId.isBlank()) {
                _todoItem.value = TodoItem()
            } else {
                _todoItem.value = todoItemRepository.getTodoItem(itemId)
            }
        }
    }

    fun saveItem(
        item: TodoItem,
        showErrorSnackbar: (ErrorMessage) -> Unit
    ) {
        val ownerId = authRepository.currentUser?.uid

        if (ownerId.isNullOrBlank()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.could_not_find_account))
            return
        }

        if (item.title.isBlank()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.item_without_title))
            return
        }

        launchCatching {
            if (itemId.isBlank()) {
                todoItemRepository.create(item.copy(ownerId = ownerId))
            } else {
                todoItemRepository.update(item)
            }

            _navigateToTodoList.value = true
        }
    }

    fun deleteItem(item: TodoItem) {
        launchCatching {
            if (itemId.isNotBlank()) todoItemRepository.delete(item.id)
            _navigateToTodoList.value = true
        }
    }
}