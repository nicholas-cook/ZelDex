package com.nickcook.zeldex.core.testing.fake

import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import com.nickcook.zeldex.core.testing.data.compendiumEntries
import com.nickcook.zeldex.core.testing.data.monsterEntry

class FakeCompendiumRepository : CompendiumRepository {

    var entriesResult: Result<List<CompendiumEntry>> = Result.Success(compendiumEntries)
    var entryResult: Result<CompendiumEntry> = Result.Success(monsterEntry)

    override suspend fun getCompendiumList(refresh: Boolean): Result<List<CompendiumEntry>> =
        entriesResult

    override suspend fun getCategoryList(
        categoryName: String,
        refresh: Boolean
    ): Result<List<CompendiumEntry>> = entriesResult

    override suspend fun getCompendiumEntry(entryId: Int): Result<CompendiumEntry> = entryResult
}