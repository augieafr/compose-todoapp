package com.augieafr.todo_app.profile

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.augieafr.todo_app.HiltActivity
import com.augieafr.todo_app.TodoApp
import com.augieafr.todo_app.assertCurrentRouteName
import com.augieafr.todo_app.ui.navigation.Screen
import com.augieafr.todo_app.ui.theme.TODOAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MyProfileScreenKtTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltActivity>()
    private lateinit var context: Context
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            TODOAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                TodoApp(
                    modifier = Modifier.fillMaxSize(), navController = navController
                )
            }
        }
        context = composeTestRule.activity
    }

    @Test
    fun assert_all_information_displayed(): Unit = with(composeTestRule) {
        // navigate to profile screen
        onNodeWithContentDescription("About me").performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        onNodeWithText("Augie Afriyansyah").assertIsDisplayed()
        onNodeWithText("An Associate Android Developer who loves to learn new things, gaming, and watching movies. Let's connect!").assertIsDisplayed()
        onNodeWithContentDescription("github").assertIsDisplayed()
        onNodeWithContentDescription("linkedin").assertIsDisplayed()
        onNodeWithContentDescription("instagram").assertIsDisplayed()
        onNodeWithContentDescription("Profile Picture").assertIsDisplayed()
    }
}