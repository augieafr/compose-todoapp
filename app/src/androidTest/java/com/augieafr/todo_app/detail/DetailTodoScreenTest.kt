package com.augieafr.todo_app.detail

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.augieafr.todo_app.HiltActivity
import com.augieafr.todo_app.TodoApp
import com.augieafr.todo_app.addTodo
import com.augieafr.todo_app.addTodoDatePickerHelper
import com.augieafr.todo_app.ui.theme.TODOAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DetailTodoScreenTest {
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
    fun navigate_to_detail_then_edit(): Unit = with(composeTestRule) {
        addTodo(context, "test todo", "description", 1)
        // navigate to detail screen
        onAllNodesWithText("test todo")[0].performClick()
        onNodeWithText("Detail Todo").assertIsDisplayed()

        // edit to do
        onNodeWithContentDescription("Edit Todo").performClick()
        onNodeWithTag("title").run {
            assertIsEnabled()
            performTextClearance()
            performTextInput("edited todo")
        }
        onNodeWithContentDescription("Undo").isDisplayed()
        onNodeWithTag("description").run {
            assertIsEnabled()
            performTextClearance()
            performTextInput("edited description")
        }
        onNodeWithContentDescription("date picker").performClick()
        val editedDueDate = addTodoDatePickerHelper(this, 3)
        // save to do then assert
        onNodeWithContentDescription("Edit Todo").performClick()
        // assert edited todo
        onNodeWithText("edited todo").assertIsDisplayed()
        onNodeWithText("edited description").assertIsDisplayed()
        onNodeWithText(editedDueDate).assertIsDisplayed()
    }

    @Test
    fun navigate_to_detail_then_assert_can_not_edit(): Unit = with(composeTestRule) {
        addTodo(context, "test todo", "description", 1)
        // navigate to detail screen
        onAllNodesWithText("test todo")[0].performClick()
        onNodeWithText("Detail Todo").assertIsDisplayed()
        onNodeWithText("test todo").assertIsDisplayed().assertIsNotEnabled()
        onNodeWithText("description").assertIsDisplayed().assertIsNotEnabled()
    }
}