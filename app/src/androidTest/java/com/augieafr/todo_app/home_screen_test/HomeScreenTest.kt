package com.augieafr.todo_app.home_screen_test

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.augieafr.todo_app.HiltActivity
import com.augieafr.todo_app.R
import com.augieafr.todo_app.addTodo
import com.augieafr.todo_app.ui.home.HomeScreen
import com.augieafr.todo_app.ui.theme.TODOAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltActivity>()
    private lateinit var context: Context

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            TODOAppTheme {
                HomeScreen(navigateToDetail = {}) {

                }
            }
        }
        context = composeTestRule.activity
    }

    @Test
    fun done_todo(): Unit = with(composeTestRule) {
        addTodo(context, "test", "description", 1)
        // at first there is no to do done yet
        onNodeWithText("Done").assertIsNotDisplayed()
        onAllNodesWithTag("Checkbox")[0].performClick()
        onNodeWithTag("TodoList").performTouchInput {
            this.swipeUp()
        }
        // validate to do Done is displayed
        onNodeWithText("Done").assertIsDisplayed()
    }

    @Test
    fun delete_Todo(): Unit = with(composeTestRule) {
        // add to do with 1 day left so it will display in first index
        addTodo(context, "will be deleted", "description", 1)
        // delete to do at first index
        onAllNodesWithContentDescription("Delete button")[0].performClick()
        // validate success delete
        onNodeWithTag("will be deleted").assertDoesNotExist()
    }

    @Test
    fun search_todo(): Unit = with(composeTestRule) {
        // add to do to search
        addTodo(context, "random title of todo", "description", 2)
        onNodeWithContentDescription("Search").performClick()
        // search wrong case and should display no to do found
        onNodeWithTag(context.getString(R.string.search_bar_test_tag)).assertIsDisplayed()
            .performTextInput("this is wrong case")
        onNodeWithTag(context.getString(R.string.empty_todo_test_tag)).assertTextEquals(
            context.getString(
                R.string.empty_todo_message
            )
        )

        // positive case
        onNodeWithTag(context.getString(R.string.search_bar_test_tag)).assertIsDisplayed().run {
            performTextClearance()
            performTextInput("random")
        }
        onNodeWithText("random title of todo").assertIsDisplayed()
    }

    @Test
    fun success_add_todo(): Unit = with(composeTestRule) {
        addTodo(context, "Title test", "Description test", 4)
        // validate to do displayed in home screen and it is match
        onNodeWithText("Title test").assertIsDisplayed()
        onNodeWithText("Description test").assertIsDisplayed()
        onNodeWithText("4 days left").assertIsDisplayed()
    }

    @Test
    fun group_by_deadline(): Unit = with(composeTestRule) {
        // add to do with near deadline
        addTodo(context, "First todo", "First description", 2)
        // add to do with mid deadline
        addTodo(context, "Second todo", "Second description", 5)
        // add to do with far deadline
        addTodo(context, "Third todo", "Third description", 8)

        // now should display to do group by isDone
        onNodeWithText("To do").assertIsDisplayed()
        // change to group by deadline
        onNodeWithContentDescription("Group by").performClick()
        // validate group by deadline
        onNodeWithText("Near").assertIsDisplayed()
        onNodeWithText("Mid").assertIsDisplayed()
        onNodeWithText("Far").assertIsDisplayed()
    }

    @Test
    fun add_todo_then_cancel(): Unit = with(composeTestRule) {
        // click fab add button
        onNodeWithTag(context.getString(R.string.fab_test_tag)).performClick()
        // assert AddTodoDialog is display
        onNodeWithTag(context.getString(R.string.add_todo_dialog_test_tag)).assertIsDisplayed()
        // cancel
        onNodeWithContentDescription(context.getString(R.string.close_content_desc)).performClick()
        // should display empty to do again
        onNodeWithTag(context.getString(R.string.empty_todo_test_tag))
            .assertTextEquals(context.getString(R.string.empty_todo_message))
    }
}
