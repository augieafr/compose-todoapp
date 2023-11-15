package com.augieafr.todo_app.ui.component.todo_appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.ui.model.GroupBy
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.ui.theme.TODOAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(
    route: String?,
    onNavigationUp: (() -> Unit)? = null,
    isShowTitle: Boolean = true,
    actions: @Composable () -> Unit
) {
    val title = when (route) {
        Screen.Home.route -> "Todo App"
        Screen.DetailTodo.route -> "Detail Todo"
        Screen.Profile.route -> "About Me"
        else -> return
    }

    @Composable
    fun NavigationUp() {
        Icon(
            modifier = Modifier.clickable { onNavigationUp?.invoke() },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Navigation Up"
        )
    }

    TopAppBar(
        windowInsets = WindowInsets(right = 8.dp),
        title = { if (isShowTitle) Text(text = title) },
        actions = {
            actions()
        },
        navigationIcon = {
            if (route != Screen.Home.route) NavigationUp()
        }
    )
}

@Preview
@Composable
fun TodoAppBarPreview() {
    TODOAppTheme {
        TodoAppBar(route = Screen.DetailTodo.route, onNavigationUp = {}) {
            Screen.Home.TopBarActions(
                query = "",
                isSearchBarActive = true,
                groupBy = GroupBy.DEADLINE,
                onCancelSearch = {},
                onGroupByChanged = {},
                onQueryChanged = {}) {

            }
        }
    }
}