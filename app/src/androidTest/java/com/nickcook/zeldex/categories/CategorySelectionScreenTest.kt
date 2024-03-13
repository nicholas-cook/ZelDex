package com.nickcook.zeldex.categories

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nickcook.zeldex.ui.theme.ZelDexTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategorySelectionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun categoryScreenTest() {
        composeTestRule.setContent {
            ZelDexTheme {
                CategorySelectionScreen(onCategoryClicked = {})
            }
        }
        composeTestRule.onNodeWithText("CREATURES").assertExists()
        composeTestRule.onNodeWithText("MONSTERS").assertExists()
        composeTestRule.onNodeWithText("MATERIALS").assertExists()
        composeTestRule.onNodeWithText("EQUIPMENT").assertExists()
        composeTestRule.onNodeWithText("TREASURE").assertExists()
        composeTestRule.onNodeWithText("ALL").assertExists()
    }
}