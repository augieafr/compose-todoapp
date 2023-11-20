package com.augieafr.todo_app

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import org.junit.Assert
import java.text.SimpleDateFormat
import java.util.Calendar

fun addTodoDatePickerHelper(testRule: AndroidComposeTestRule<*, *>, daysFromNow: Int): String {
    val calendar = with(Calendar.getInstance()) {
        add(Calendar.DAY_OF_YEAR, daysFromNow)
        this.time
    }

    // note for future me: in case you are wondering where the date pattern come from, try uncomment
    // testRule.onAllNodes(isRoot())[2].printToLog("Tree:")
    val sdf = SimpleDateFormat("EEEE, MMMM dd, yyyy")
    val date = sdf.format(calendar)
    testRule.onNodeWithText(date).assertIsDisplayed().performClick()
    // validate if save button enabled, then save to do
    testRule.onNodeWithText("Save").assertIsEnabled().performClick()

    // return date + daysFromNow with UI date format
    return SimpleDateFormat("MMM dd, yyyy").format(calendar)
}

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

fun AndroidComposeTestRule<*, *>.addTodo(
    context: Context,
    title: String,
    description: String,
    daysFromNow: Int
) {
    onNodeWithTag(context.getString(R.string.fab_test_tag)).performClick()
    onNodeWithText(context.getString(R.string.title_text_field_placeholder)).run {
        performTextInput(title)
    }
    onNodeWithText(context.getString(R.string.description_text_field_placeholder)).run {
        performTextInput(description)
    }
    onNodeWithContentDescription(context.getString(R.string.open_date_picker_content_desc)).performClick()
    addTodoDatePickerHelper(this, daysFromNow)
    onNodeWithTag(context.getString(R.string.add_button_test_tag)).performClick()
}