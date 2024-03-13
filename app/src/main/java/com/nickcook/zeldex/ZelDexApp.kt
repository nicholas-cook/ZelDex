package com.nickcook.zeldex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nickcook.zeldex.categories.CategorySelectionRoute
import com.nickcook.zeldex.entry.CompendiumEntryRoute
import com.nickcook.zeldex.list.CompendiumListRoute
import com.nickcook.zeldex.ui.theme.ZelDexTheme

@Composable
fun ZelDexApp(navHostController: NavHostController) {
    ZelDexTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ZelDexNavHost(navHostController = navHostController)
        }
    }
}

@Composable
fun ZelDexNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = ZelDexScreen.CategorySelection.route
    ) {
        composable(route = ZelDexScreen.CategorySelection.route) {
            CategorySelectionRoute(onCategoryClicked = {
                navHostController.navigate(route = "${ZelDexScreen.CompendiumList.route}/${it.categoryName}")
            })
        }
        composable(route = "${ZelDexScreen.CompendiumList.route}/{categoryName}") {
            CompendiumListRoute(
                onItemClicked = { entryId ->
                    navHostController.navigate(route = "${ZelDexScreen.CompendiumEntry.route}/$entryId")
                },
                onNavigateUp = {
                    navHostController.popBackStack()
                }
            )
        }
        composable(route = "${ZelDexScreen.CompendiumEntry.route}/{entryId}") {
            CompendiumEntryRoute(onNavigateUp = {
                navHostController.popBackStack()
            })
        }
    }
}