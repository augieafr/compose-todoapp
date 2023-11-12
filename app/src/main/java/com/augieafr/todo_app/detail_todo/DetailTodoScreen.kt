package com.augieafr.todo_app.detail_todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailTodoScreen(modifier: Modifier = Modifier, todoId: Int, onNavigateUp: () -> Unit) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Detail Todo Screen")
    }
}