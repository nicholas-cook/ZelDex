package com.nickcook.zeldex.core.localstorage.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compendium_entries")
data class CompendiumEntryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val image: String,
    val commonLocations: String,
    val dlc: Boolean,
    val drops: String,
    val properties: String?,
    val heartsRecovered: Float?,
    val cookingEffect: String
)
