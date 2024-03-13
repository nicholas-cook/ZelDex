package com.nickcook.zeldex.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.testing.data.compendiumEntries
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompendiumListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun compendiumListScreenTest() {
        composeTestRule.setContent {
            CompendiumListScreen(
                screenState = CompendiumListScreenState.Success(
                    CompendiumCategory.ALL,
                    compendiumEntries
                ),
                onItemClicked = {},
                onSearch = {},
                onRefresh = {},
                onNavigateUp = {})
        }
        composeTestRule.onNodeWithText("BOKOBLIN").assertExists()
        composeTestRule.onNodeWithText("APPLE").assertExists()
        composeTestRule.onNodeWithText("ANCIENT BATTLE AXE").assertExists()
        composeTestRule.onNodeWithText("BLUE-WINGED HERON").assertExists()
        composeTestRule.onNodeWithText("AMBER").assertExists()
    }
}