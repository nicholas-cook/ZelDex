package com.nickcook.zeldex.core.localstorage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nickcook.zeldex.core.localstorage.room.entity.CompendiumEntryEntity

@Dao
interface CompendiumEntryDao {

    @Query("SELECT * FROM compendium_entries sort ORDER BY id ASC")
    suspend fun getCompendiumEntries(): List<CompendiumEntryEntity>

    @Query("SELECT * FROM compendium_entries WHERE category = :category ORDER BY id ASC")
    suspend fun getCompendiumEntriesForCategory(category: String): List<CompendiumEntryEntity>

    @Query("SELECT * FROM compendium_entries WHERE id = :id")
    suspend fun getCompendiumEntry(id: Int): CompendiumEntryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompendiumEntries(compendiumEntries: List<CompendiumEntryEntity>)
}
