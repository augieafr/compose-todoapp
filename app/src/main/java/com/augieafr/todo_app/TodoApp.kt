package com.augieafr.todo_app

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.augieafr.todo_app.ui.detail_todo.DetailTodoScreen
import com.augieafr.todo_app.ui.home.HomeScreen
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.ui.profile.MyProfileScreen

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = {
            EnterTransition.None
        },
        builder = {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier,
                    navigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    navigateToDetail = {
                        navController.navigate(Screen.DetailTodo.createRoute(it))

                    })
            }
            composable(
                route = Screen.DetailTodo.route,
                arguments = listOf(navArgument(Screen.TODO_ID_KEY) {
                    type = NavType.IntType
                })
            ) {
                val todoId = it.arguments?.getInt(Screen.TODO_ID_KEY) ?: -1
                DetailTodoScreen(modifier = modifier, todoId = todoId, onNavigateUp = {
                    navController.navigateUp()
                })
            }
            composable(Screen.Profile.route) {
                val context = LocalContext.current
                MyProfileScreen(modifier = modifier, onNavigateUp = {
                    navController.navigateUp()
                }, onNavigateToBrowser = {
                    val webpage: Uri = Uri.parse(it)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    context.startActivity(intent)
                })
            }
        })
}