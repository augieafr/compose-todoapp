package com.augieafr.todo_app.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.augieafr.todo_app.ui.component.text_field.SearchBarTextField

sealed class Screen(val route: String) {
    data object Home : Screen("home") {
        @Composable
        fun TopBarActions(
            query: String,
            isSearchBarActive: Boolean,
            onCancelSearch: () -> Unit,
            onQueryChanged: (String) -> Unit,
            onActionClicked: () -> Unit
        ) {
            AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = isSearchBarActive) {
                SearchBarTextField(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = {
                        onQueryChanged(it)
                    },
                    onCancelSearch = {
                        onCancelSearch()
                    }
                )
            }
            if (!isSearchBarActive) {
                Icon(
                    modifier = Modifier.clickable { onCancelSearch() },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    modifier = Modifier.clickable { onActionClicked() },
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "About me",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    data object Profile : Screen("profile")
    data object AddTodo : Screen("add")
    data object DetailTodo : Screen("home/{$TODO_ID_KEY}") {
        @Composable
        fun TopBarActions(onActionClicked: () -> Unit) {
            var isOnEdit by rememberSaveable {
                mutableStateOf(false)
            }
            val modifier = Modifier.clickable {
                isOnEdit = !isOnEdit
                onActionClicked()
            }
            Icon(
                modifier = modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = if (isOnEdit) Icons.Filled.Done else Icons.Filled.Edit,
                contentDescription = "Edit Todo"
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                modifier = modifier,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                text = if (isOnEdit) "Done" else "Edit"
            )
        }

        fun createRoute(todoId: Int) = "home/$todoId"
    }

    companion object {
        const val TODO_ID_KEY = "todoId"
    }
}