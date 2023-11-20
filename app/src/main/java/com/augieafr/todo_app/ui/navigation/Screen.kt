package com.augieafr.todo_app.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.augieafr.todo_app.R
import com.augieafr.todo_app.ui.component.GroupByIconAnimated
import com.augieafr.todo_app.ui.component.text_field.SearchBarTextField
import com.augieafr.todo_app.ui.model.GroupBy
import com.augieafr.todo_app.utils.noRippleClickable

sealed class Screen(val route: String) {
    data object Home : Screen("home") {
        @Composable
        fun TopBarActions(
            query: String,
            isSearchBarActive: Boolean,
            groupBy: GroupBy,
            onCancelSearch: () -> Unit,
            onGroupByChanged: () -> Unit,
            onQueryChanged: (String) -> Unit,
            navigateToProfileScreen: () -> Unit
        ) {
            AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = isSearchBarActive) {
                SearchBarTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    query = query,
                    placeholder = "Search by title",
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
                GroupByIconAnimated(groupBy = groupBy, onGroupByChanged)
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    modifier = Modifier
                        .clickable { navigateToProfileScreen() },
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "About me",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }

    data object Profile : Screen("profile")
    data object DetailTodo : Screen("home/{$TODO_ID_KEY}") {
        @Composable
        fun TopBarActions(
            isOnEdit: Boolean,
            hasError: Boolean,
            canUndo: Boolean,
            onSaveEditClicked: () -> Unit,
            onUndoClicked: () -> Unit
        ) {
            val editColor =
                if (hasError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            if (canUndo) {
                Icon(
                    modifier = Modifier
                        .noRippleClickable {
                            onUndoClicked()
                        }
                        .size(18.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.baseline_undo_24),
                    contentDescription = "Undo"
                )
                Spacer(modifier = Modifier.size(14.dp))
            }
            Icon(
                modifier = Modifier
                    .noRippleClickable {
                        onSaveEditClicked()
                    }
                    .size(18.dp),
                tint = editColor,
                imageVector = if (isOnEdit) Icons.Filled.Done else Icons.Filled.Edit,
                contentDescription = "Edit Todo"
            )
            Spacer(modifier = Modifier.size(16.dp))
        }

        fun createRoute(todoId: Int) = "home/$todoId"
    }

    companion object {
        const val TODO_ID_KEY = "todoId"
    }
}