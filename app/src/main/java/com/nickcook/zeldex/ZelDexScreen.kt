package com.nickcook.zeldex

sealed class ZelDexScreen(val route: String) {
    data object CategorySelection : ZelDexScreen("categorySelection")
    data object CompendiumList : ZelDexScreen("compendiumList")
    data object CompendiumEntry : ZelDexScreen("compendiumEntry")
}