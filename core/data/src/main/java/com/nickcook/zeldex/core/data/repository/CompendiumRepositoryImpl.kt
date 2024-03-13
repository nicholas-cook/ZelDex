package com.nickcook.zeldex.core.data.repository

import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.CompendiumEntry.Companion.toCompendiumEntryEntity
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.localstorage.room.dao.CompendiumEntryDao
import com.nickcook.zeldex.core.network.services.HyruleCompendiumService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class CompendiumRepositoryImpl @Inject constructor(
    private val hyruleCompendiumService: HyruleCompendiumService,
    private val compendiumEntryDao: CompendiumEntryDao
) : CompendiumRepository {

    override suspend fun getCompendiumList(refresh: Boolean): Result<List<CompendiumEntry>> {
        if (!refresh) {
            val localData = compendiumEntryDao.getCompendiumEntries()
            if (localData.map { it.category }
                    .containsAll(CompendiumCategory.entries.map { it.categoryName }.take(5))) {
                return Result.Success(localData.map { CompendiumEntry.fromCompendiumEntryEntity(it) })
            }
        }
        return when (val response = hyruleCompendiumService.getAllEntries()) {
            is ApiResponse.Success -> {
                val entries =
                    response.data.data.map {
                        CompendiumEntry.fromCompendiumItemResponse(it)
                    }
                compendiumEntryDao.insertCompendiumEntries(entries.map { it.toCompendiumEntryEntity() })
                Result.Success(entries.sortedBy { it.id })
            }

            is ApiResponse.Failure.Error -> {
                Result.Error(response.toString())
            }

            is ApiResponse.Failure.Exception -> {
                Result.Error(response.exception.message)
            }
        }
    }

    override suspend fun getCategoryList(
        categoryName: String,
        refresh: Boolean
    ): Result<List<CompendiumEntry>> {
        if (!refresh) {
            val localData = compendiumEntryDao.getCompendiumEntriesForCategory(categoryName)
            if (localData.isNotEmpty()) {
                return Result.Success(localData.map { CompendiumEntry.fromCompendiumEntryEntity(it) })
            }
        }
        return when (val response = hyruleCompendiumService.getEntriesForCategory(categoryName)) {
            is ApiResponse.Success -> {
                val entries =
                    response.data.data.map {
                        CompendiumEntry.fromCompendiumItemResponse(it)
                    }
                compendiumEntryDao.insertCompendiumEntries(entries.map { it.toCompendiumEntryEntity() })
                Result.Success(entries.sortedBy { it.id })
            }

            is ApiResponse.Failure.Error -> {
                Result.Error(response.toString())
            }

            is ApiResponse.Failure.Exception -> {
                Result.Error(response.exception.message)
            }
        }
    }

    override suspend fun getCompendiumEntry(entryId: Int): Result<CompendiumEntry> {
        val localData = compendiumEntryDao.getCompendiumEntry(entryId)
        if (localData != null) {
            return Result.Success(CompendiumEntry.fromCompendiumEntryEntity(localData))
        }
        return when (val response = hyruleCompendiumService.getCompendiumEntry(entryId)) {
            is ApiResponse.Success -> {
                val entry = CompendiumEntry.fromCompendiumItemResponse(response.data.data)
                compendiumEntryDao.insertCompendiumEntries(listOf(entry.toCompendiumEntryEntity()))
                Result.Success(entry)
            }

            is ApiResponse.Failure.Error -> {
                Result.Error(toString())
            }

            is ApiResponse.Failure.Exception -> {
                Result.Error(response.exception.message)
            }
        }
    }
}
