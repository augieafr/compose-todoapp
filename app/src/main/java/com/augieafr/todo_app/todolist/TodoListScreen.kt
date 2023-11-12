package com.augieafr.todo_app.todolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.augieafr.todo_app.MainViewModel
import com.augieafr.todo_app.ui.component.AddEditTodoDialog
import com.augieafr.todo_app.ui.component.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    modifier: Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    // A surface container using the 'background' color from the theme
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { Text(text = "Todo App") }, actions = {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "About me"
            )
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.todoEventHandler(null, ListTodoScreenEvent.Add)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) { paddingValues ->
        val listTodo = viewModel.todoList.collectAsState(emptyList())

        if (listTodo.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = "No Todo"
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
            ) {
                items(listTodo.value, key = {
                    it.id
                }) { todo ->
                    Todo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(IntrinsicSize.Max),
                        title = todo.title,
                        description = todo.description,
                        isDone = todo.isDone,
                        deadline = todo.deadLine,
                        onTodoEvent = { event ->
                            viewModel.todoEventHandler(todo, event)
                        }
                    )
                }
            }
        }

        with(viewModel.isShowAddEditTodo) {
            if (first) {
                AddEditTodoDialog(
                    modifier = Modifier
                        .fillMaxWidth(),
                    isEdit = second,
                    onDismissRequest = {
                        viewModel.isShowAddEditTodo = Pair(false, false)
                    },
                    onSaveClicked = { title, description, dueDate ->
                        viewModel.todoEventHandler(
                            null,
                            ListTodoScreenEvent.SaveTodo(title, description, dueDate)
                        )
                    }
                )
            }
        }
    }
}