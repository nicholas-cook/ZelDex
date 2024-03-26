package com.nickcook.zeldex.core.testing.fake

import com.nickcook.zeldex.core.network.model.CompendiumEntryResponse
import com.nickcook.zeldex.core.network.model.CompendiumListResponse
import com.nickcook.zeldex.core.network.services.HyruleCompendiumService
import com.nickcook.zeldex.core.testing.data.successCompendiumEntriesResponse
import com.nickcook.zeldex.core.testing.data.successCompendiumEntryResponse
import com.skydoves.sandwich.ApiResponse

class FakeHyruleCompendiumService : HyruleCompendiumService {

    var entryResponse: ApiResponse<CompendiumEntryResponse> = successCompendiumEntryResponse
    var entriesResponse: ApiResponse<CompendiumListResponse> = successCompendiumEntriesResponse

    override suspend fun getAllEntries(): ApiResponse<CompendiumListResponse> = entriesResponse

    override suspend fun getEntriesForCategory(categoryName: String): ApiResponse<CompendiumListResponse> =
        entriesResponse

    override suspend fun getCompendiumEntry(entryId: Int): ApiResponse<CompendiumEntryResponse> =
        entryResponse
}