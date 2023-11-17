package com.augieafr.todo_app.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.augieafr.todo_app.ui.component.Todo
import com.augieafr.todo_app.ui.component.TodoStickyHeader
import com.augieafr.todo_app.ui.component.todo_appbar.TodoAppBar
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.ui.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToAddTodo: () -> Unit
) {
    var isSearchBarActive by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(modifier = modifier, topBar = {
        TodoAppBar(
            route = Screen.Home.route, isShowTitle = !isSearchBarActive, actions = {
                Screen.Home.TopBarActions(
                    query = viewModel.searchQuery.collectAsState().value,
                    isSearchBarActive = isSearchBarActive,
                    groupBy = viewModel.todoGroupBy.collectAsState().value,
                    onCancelSearch = { isSearchBarActive = !isSearchBarActive },
                    onQueryChanged = { viewModel.setQuery(it) },
                    onGroupByChanged = { viewModel.changeGroupBy() },
                    navigateToProfileScreen = { navigateToProfile() })
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navigateToAddTodo()
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
    ) { paddingValues ->
        val listTodo = viewModel.todoList.collectAsState(emptyMap())
        HomeScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            listTodo = listTodo.value,
            onDeleteTodo = { viewModel.deleteTodo(it) },
            navigateToDetail = { navigateToDetail(it) },
            onIsDoneClicked = { viewModel.setTodoIsDone(it) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    listTodo: Map<String, List<TodoUiModel>>,
    onDeleteTodo: (TodoUiModel) -> Unit,
    navigateToDetail: (Int) -> Unit,
    onIsDoneClicked: (TodoUiModel) -> Unit
) {
    if (listTodo.isEmpty()) {
        Box(
            modifier = modifier
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "No todo found"
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
        ) {
            listTodo.forEach { (key, value) ->
                stickyHeader {
                    TodoStickyHeader(key)
                }

                items(value, key = {
                    it.id
                }) { todo ->
                    Todo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(IntrinsicSize.Max)
                            .animateItemPlacement(),
                        title = todo.title,
                        description = todo.description,
                        isDone = todo.isDone,
                        deadline = todo.deadLine,
                        onTodoEvent = { event ->
                            when (event) {
                                HomeScreenEvent.Delete -> onDeleteTodo(todo)
                                HomeScreenEvent.Detail -> navigateToDetail(todo.id)
                                is HomeScreenEvent.Done -> onIsDoneClicked(todo.copy(isDone = event.isDone))
                            }
                        }
                    )
                }
            }
        }
    }
}