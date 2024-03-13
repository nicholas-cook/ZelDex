package com.nickcook.zeldex.core.localstorage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nickcook.zeldex.core.localstorage.room.dao.CompendiumEntryDao
import com.nickcook.zeldex.core.localstorage.room.entity.CompendiumEntryEntity

@Database(entities = [CompendiumEntryEntity::class], version = 1, exportSchema = false)
abstract class ZelDexDatabase : RoomDatabase() {

    abstract fun compendiumEntryDao(): CompendiumEntryDao
}
