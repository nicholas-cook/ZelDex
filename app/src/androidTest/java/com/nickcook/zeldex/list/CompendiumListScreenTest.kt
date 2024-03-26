package com.nickcook.zeldex.list

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
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
        scrollToItemAndAssertExists("BOKOBLIN")
        scrollToItemAndAssertExists("APPLE")
        scrollToItemAndAssertExists("ANCIENT BATTLE AXE")
        scrollToItemAndAssertExists("BLUE-WINGED HERON")
        scrollToItemAndAssertExists("AMBER")
    }

    private fun scrollToItemAndAssertExists(targetText: String) {
        composeTestRule.onNodeWithTag("compendium_list").performScrollToNode(hasText(targetText)).assertExists()
    }
}