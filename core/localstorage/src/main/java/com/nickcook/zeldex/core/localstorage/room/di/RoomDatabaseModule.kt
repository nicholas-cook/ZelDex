package com.nickcook.zeldex.core.localstorage.room.di

import android.content.Context
import androidx.room.Room
import com.nickcook.zeldex.core.localstorage.room.ZelDexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideZelDexDatabase(@ApplicationContext context: Context): ZelDexDatabase {
        return Room.databaseBuilder(context, ZelDexDatabase::class.java, "zeldex_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideCompendiumEntryDao(database: ZelDexDatabase) = database.compendiumEntryDao()
}
