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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.augieafr.todo_app.R
import com.augieafr.todo_app.ui.component.AddTodoDialog
import com.augieafr.todo_app.ui.component.Todo
import com.augieafr.todo_app.ui.component.TodoStickyHeader
import com.augieafr.todo_app.ui.component.todo_appbar.TodoAppBar
import com.augieafr.todo_app.ui.model.TodoUiModel
import com.augieafr.todo_app.ui.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    var isSearchBarActive by rememberSaveable {
        mutableStateOf(false)
    }
    var isShowAddTodoDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(modifier = modifier, topBar = {
        TodoAppBar(
            route = Screen.Home.route, isShowTitle = !isSearchBarActive, actions = {
                Screen.Home.TopBarActions(
                    query = homeViewModel.searchQuery.collectAsState().value,
                    isSearchBarActive = isSearchBarActive,
                    groupBy = homeViewModel.todoGroupBy.collectAsState().value,
                    onCancelSearch = { isSearchBarActive = !isSearchBarActive },
                    onQueryChanged = { homeViewModel.setQuery(it) },
                    onGroupByChanged = { homeViewModel.changeGroupBy() },
                    navigateToProfileScreen = { navigateToProfile() })
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.testTag(stringResource(R.string.fab_test_tag)),
            onClick = {
                isShowAddTodoDialog = true
            }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
    ) { paddingValues ->
        val listTodo = homeViewModel.todoList.collectAsState(emptyMap())
        if (isShowAddTodoDialog) AddTodoDialog(
            modifier = Modifier.testTag(stringResource(R.string.add_todo_dialog_test_tag)),
            onDismissDialog = { isShowAddTodoDialog = false },
            onAddTodo = { title, description, dueDate ->
                homeViewModel.addTodo(title, description, dueDate)
            })
        HomeScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            listTodo = listTodo.value,
            onDeleteTodo = { homeViewModel.deleteTodo(it) },
            navigateToDetail = { navigateToDetail(it) },
            onIsDoneClicked = { homeViewModel.setTodoIsDone(it) }
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
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(stringResource(id = R.string.empty_todo_test_tag)),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = stringResource(id = R.string.empty_todo_message)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.testTag("TodoList"),
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
                                TodoEvent.Delete -> onDeleteTodo(todo)
                                TodoEvent.Detail -> navigateToDetail(todo.id)
                                is TodoEvent.Done -> onIsDoneClicked(todo.copy(isDone = event.isDone))
                            }
                        }
                    )
                }
            }
        }
    }
}