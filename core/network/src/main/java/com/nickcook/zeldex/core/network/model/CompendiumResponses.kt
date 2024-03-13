package com.nickcook.zeldex.core.network.model

import com.squareup.moshi.Json

data class CompendiumListResponse(val data: List<CompendiumItemResponse>)

data class CompendiumEntryResponse(val data: CompendiumItemResponse)

data class CompendiumItemResponse(
    val name: String,
    val id: Int,
    val category: String,
    val image: String,
    val description: String,
    @Json(name = "common_locations")
    val commonLocations: List<String>?,
    val dlc: Boolean,
    val drops: List<String>?,
    val properties: PropertiesResponse?,
    @Json(name = "hearts_recovered")
    val heartsRecovered: Float?,
    @Json(name = "cooking_effect")
    val cookingEffect: String?
)

data class PropertiesResponse(
    val attack: Int?,
    val defense: Int?
)
