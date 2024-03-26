package com.nickcook.zeldex.core.testing.fake

import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.CompendiumEntry.Companion.toCompendiumEntryEntity
import com.nickcook.zeldex.core.localstorage.room.dao.CompendiumEntryDao
import com.nickcook.zeldex.core.localstorage.room.entity.CompendiumEntryEntity

class FakeCompendiumEntryDao : CompendiumEntryDao {

    var entryToReturn: CompendiumEntry? = null
    var entriesToReturn: List<CompendiumEntry> = emptyList()

    override suspend fun getCompendiumEntries(): List<CompendiumEntryEntity> = entriesToReturn.map { it.toCompendiumEntryEntity() }

    override suspend fun getCompendiumEntriesForCategory(category: String): List<CompendiumEntryEntity> = entriesToReturn.map { it.toCompendiumEntryEntity() }

    override suspend fun getCompendiumEntry(id: Int): CompendiumEntryEntity? = entryToReturn?.toCompendiumEntryEntity()

    override suspend fun insertCompendiumEntries(compendiumEntries: List<CompendiumEntryEntity>) {
        // No-op
    }
}