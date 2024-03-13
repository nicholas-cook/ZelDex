package com.nickcook.zeldex.core.network.services

import com.nickcook.zeldex.core.network.model.CompendiumEntryResponse
import com.nickcook.zeldex.core.network.model.CompendiumListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HyruleCompendiumService {

    @GET("all")
    suspend fun getAllEntries(): ApiResponse<CompendiumListResponse>

    @GET("category/{categoryName}")
    suspend fun getEntriesForCategory(@Path("categoryName") categoryName: String): ApiResponse<CompendiumListResponse>

    @GET("entry/{id}")
    suspend fun getCompendiumEntry(@Path("id") entryId: Int): ApiResponse<CompendiumEntryResponse>
}
