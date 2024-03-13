package com.nickcook.zeldex.core.data.di

import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import com.nickcook.zeldex.core.data.repository.CompendiumRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CompendiumModule {

    @Binds
    @Singleton
    fun bindsCompendiumRepository(compendiumRepositoryImpl: CompendiumRepositoryImpl): CompendiumRepository
}
