package com.nickcook.zeldex.core.testing.fake

import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import com.nickcook.zeldex.core.testing.data.compendiumEntries

class FakeCompendiumRepository : CompendiumRepository {

    var isErrorResponse = false

    override suspend fun getCompendiumList(refresh: Boolean): Result<List<CompendiumEntry>> {
        return if (isErrorResponse) {
            Result.Error("Test exception")
        } else {
            Result.Success(compendiumEntries)
        }
    }

    override suspend fun getCategoryList(
        categoryName: String,
        refresh: Boolean
    ): Result<List<CompendiumEntry>> {
        return if (isErrorResponse) {
            Result.Error("Test exception")
        } else {
            Result.Success(compendiumEntries.filter {
                it.category == CompendiumCategory.fromCategoryName(
                    categoryName
                )
            })
        }
    }

    override suspend fun getCompendiumEntry(entryId: Int): Result<CompendiumEntry> {
        return if (isErrorResponse) {
            Result.Error("Test exception")
        } else {
            val entry = compendiumEntries.find { it.id == entryId }
            if (entry != null) {
                Result.Success(entry)
            } else {
                Result.Error("Entry not found")
            }
        }
    }
}