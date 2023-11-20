package com.augieafr.todo_app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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