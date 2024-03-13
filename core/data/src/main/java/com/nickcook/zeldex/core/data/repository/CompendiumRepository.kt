package com.nickcook.zeldex.core.data.repository

import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result

interface CompendiumRepository {

    suspend fun getCompendiumList(refresh: Boolean = false): Result<List<CompendiumEntry>>

    suspend fun getCategoryList(
        categoryName: String,
        refresh: Boolean = false
    ): Result<List<CompendiumEntry>>

    suspend fun getCompendiumEntry(entryId: Int): Result<CompendiumEntry>
}
