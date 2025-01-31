package com.google.firebase.example.makeitso.data.model

import com.google.firebase.firestore.DocumentId

//TODO: Start using this data class when we add multiple lists feature
data class TodoList(
    @DocumentId val id: String = "",
    val title: String = "",
    val owner: String = "",
    val editors: List<String> = emptyList(),
    val viewers: List<String> = emptyList()
)