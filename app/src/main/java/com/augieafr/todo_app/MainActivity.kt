package com.augieafr.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.augieafr.todo_app.todolist.ToDoListScreen
import com.augieafr.todo_app.ui.theme.TODOAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOAppTheme {
                ToDoListScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
