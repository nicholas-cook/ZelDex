package com.nickcook.zeldex.list

import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry

sealed class CompendiumListScreenState(open val category: CompendiumCategory) {
    data class Loading(override val category: CompendiumCategory) :
        CompendiumListScreenState(category)

    data class Success(
        override val category: CompendiumCategory,
        val listEntries: List<CompendiumEntry>
    ) :
        CompendiumListScreenState(category)

    data class Error(override val category: CompendiumCategory) :
        CompendiumListScreenState(category)
}