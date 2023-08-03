package com.augieafr.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.augieafr.todo_app.todolist.ToDoListScreen
import com.augieafr.todo_app.ui.theme.TODOAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TODOAppTheme {
                ToDoListScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel
                )
            }

        }
    }
}
