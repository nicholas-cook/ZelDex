package com.nickcook.zeldex.entry

import com.nickcook.zeldex.core.data.model.CompendiumEntry

sealed class CompendiumEntryScreenState {
    data object Loading : CompendiumEntryScreenState()
    data class Success(val entry: CompendiumEntry) : CompendiumEntryScreenState()
    data class Error(val message: String) : CompendiumEntryScreenState()
}