package com.augieafr.todo_app.ui.detail_todo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.augieafr.todo_app.ui.component.DateInfo
import com.augieafr.todo_app.ui.component.TodoDatePicker
import com.augieafr.todo_app.ui.component.text_field.TodoTextFieldState
import com.augieafr.todo_app.ui.component.text_field.TodoTransparentTextField
import com.augieafr.todo_app.ui.component.text_field.rememberTodoTextFieldState
import com.augieafr.todo_app.ui.component.todo_appbar.TodoAppBar
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.utils.showSnackbar
import com.augieafr.todo_app.utils.toDateMillis
import kotlinx.coroutines.CoroutineScope

@Composable
fun DetailTodoScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailTodoViewModel = hiltViewModel(),
    todoId: Int,
    onNavigateUp: () -> Unit
) {
    // get data only once
    LaunchedEffect(Unit) {
        detailViewModel.getTodo(todoId)
    }
    val todoUiModel by detailViewModel.todo.collectAsState(null)

    todoUiModel?.let { todo ->
        var isOnEdit by rememberSaveable {
            mutableStateOf(false)
        }
        val titleState = rememberTodoTextFieldState(todo.title)
        var dueDateState by rememberSaveable {
            mutableStateOf(
                todo.deadLine.dueDate
            )
        }
        val descriptionState = rememberTodoTextFieldState(todo.description)

        // using key so whenever todoUiModel value changed it will re-calculate it state
        val hasError by remember(key1 = todoUiModel) {
            derivedStateOf {
                titleState.isError || descriptionState.isError
            }
        }
        val canUndo by remember(key1 = todoUiModel) {
            derivedStateOf {
                todo.deadLine.dueDate != dueDateState ||
                        todo.title != titleState.text ||
                        todo.description != descriptionState.text
            }
        }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        BackHandler {
            handleBackPressed(
                isOnEdit,
                canUndo,
                snackbarHostState,
                scope,
                hasError,
                onHasNoError = {
                    isOnEdit = false
                },
                onSaveTodo = {
                    detailViewModel.saveTodo(
                        todo, titleState.text, descriptionState.text, dueDateState
                    )
                },
                onNavigateUp = onNavigateUp
            )
        }

        Scaffold(snackbarHost = {
            SnackbarHost(snackbarHostState)
        }, topBar = {
            TodoAppBar(route = Screen.DetailTodo.route, onNavigationUp = {
                handleBackPressed(
                    isOnEdit,
                    canUndo,
                    snackbarHostState,
                    scope,
                    hasError,
                    onHasNoError = {
                        isOnEdit = false
                    },
                    onSaveTodo = {
                        detailViewModel.saveTodo(
                            todo, titleState.text, descriptionState.text, dueDateState
                        )
                    },
                    onNavigateUp = onNavigateUp
                )
            }) {
                Screen.DetailTodo.TopBarActions(isOnEdit = isOnEdit,
                    canUndo = canUndo,
                    hasError = hasError,
                    onUndoClicked = {
                        dueDateState = todo.deadLine.dueDate
                        titleState.text = todo.title
                        descriptionState.text = todo.description
                    },
                    onSaveEditClicked = {
                        saveTodo(hasError, snackbarHostState, scope, isOnEdit, onSaveTodo = {
                            detailViewModel.saveTodo(
                                todo, titleState.text, descriptionState.text, dueDateState
                            )
                        }, onHasNoError = {
                            isOnEdit = !isOnEdit
                        })
                    })
            }
        }) { paddingValues ->
            var isShowDatePicker by rememberSaveable {
                mutableStateOf(false)
            }

            DetailTodoContent(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                isOnEdit = isOnEdit,
                titleState = titleState,
                descriptionState = descriptionState,
                dueDateState = dueDateState
            ) {
                if (isOnEdit) isShowDatePicker = true
            }

            if (isShowDatePicker) {
                TodoDatePicker(initialSelectedDateMillis = dueDateState.toDateMillis(),
                    onDismissRequest = { isShowDatePicker = false },
                    onSaveButtonClick = { dueDate ->
                        dueDateState = dueDate
                        isShowDatePicker = false
                    })
            }
        }
    }
}

private fun handleBackPressed(
    isOnEdit: Boolean,
    canUndo: Boolean,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    hasError: Boolean,
    onSaveTodo: () -> Unit,
    onHasNoError: () -> Unit,
    onNavigateUp: () -> Unit
) {
    if (!isOnEdit || !canUndo) {
        onNavigateUp()
        return
    }
    snackbarHostState.showSnackbar(message = "Please save your todo first",
        scope = scope,
        actionLabel = "Save",
        onActionPerformed = {
            saveTodo(
                hasError, snackbarHostState, scope, true, onSaveTodo, onHasNoError
            )
        })
}

@Composable
fun DetailTodoContent(
    modifier: Modifier = Modifier,
    isOnEdit: Boolean,
    titleState: TodoTextFieldState,
    descriptionState: TodoTextFieldState,
    dueDateState: String,
    isShowDatePicker: () -> Unit
) {
    Column(modifier) {
        TodoTransparentTextField(
            state = titleState,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("title"),
            isEdit = isOnEdit,
            placeHolder = "Title",
            textFieldTextStyle = MaterialTheme.typography.titleLarge,
        )
        DateInfo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp), date = dueDateState
        ) {
            isShowDatePicker()
        }
        Spacer(Modifier.size(16.dp))
        TodoTransparentTextField(
            state = descriptionState,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("description"),
            isEdit = isOnEdit,
            placeHolder = "Describe your todo here",
            textFieldTextStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}

private fun saveTodo(
    hasError: Boolean,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    isOnEdit: Boolean,
    onSaveTodo: () -> Unit,
    onHasNoError: () -> Unit
) {
    if (hasError) {
        snackbarHostState.showSnackbar(
            message = "Please fill all the fields", scope = scope
        )
        return
    }
    if (isOnEdit) {
        onSaveTodo()
    }
    onHasNoError()
}