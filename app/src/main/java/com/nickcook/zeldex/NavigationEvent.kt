package com.nickcook.zeldex

sealed class NavigationEvent(val route: String) {
    data object NavigateBack : NavigationEvent("")
    data class NavigateToCategory(val categoryName: String) :
        NavigationEvent("${ZelDexScreen.CompendiumList.route}/$categoryName")

    data class NavigateToEntry(val entryId: Int) :
        NavigationEvent("${ZelDexScreen.CompendiumEntry.route}/$entryId")
}