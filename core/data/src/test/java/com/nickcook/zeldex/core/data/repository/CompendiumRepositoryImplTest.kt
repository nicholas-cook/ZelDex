package com.nickcook.zeldex.core.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.testing.data.compendiumEntries
import com.nickcook.zeldex.core.testing.data.localCompendiumEntries
import com.nickcook.zeldex.core.testing.data.localMonsterEntry
import com.nickcook.zeldex.core.testing.data.monsterEntry
import com.nickcook.zeldex.core.testing.data.successCompendiumEntriesResponse
import com.nickcook.zeldex.core.testing.data.successCompendiumEntryResponse
import com.nickcook.zeldex.core.testing.fake.FakeCompendiumEntryDao
import com.nickcook.zeldex.core.testing.fake.FakeHyruleCompendiumService
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CompendiumRepositoryImplTest {

    private val hyruleCompendiumService = FakeHyruleCompendiumService()
    private val compendiumEntryDao = FakeCompendiumEntryDao()
    private val compendiumRepositoryImpl =
        CompendiumRepositoryImpl(hyruleCompendiumService, compendiumEntryDao)

    @Test
    fun `when local storage is empty, getCompendiumList fetches entries from network`() = runTest {
        compendiumEntryDao.entriesToReturn = emptyList()
        hyruleCompendiumService.entriesResponse = successCompendiumEntriesResponse
        val result = compendiumRepositoryImpl.getCompendiumList(refresh = false)
        assertThat(result).isInstanceOf(Result.Success::class)
        result as Result.Success
        assertThat(result.data).isEqualTo(compendiumEntries)
    }

    @Test
    fun `when local storage is not empty, getCompendiumList fetches entries from local storage`() =
        runTest {
            compendiumEntryDao.entriesToReturn = localCompendiumEntries
            val result = compendiumRepositoryImpl.getCompendiumList(refresh = false)
            assertThat(result).isInstanceOf(Result.Success::class)
            result as Result.Success
            assertThat(result.data).isEqualTo(localCompendiumEntries)
        }

    @Test
    fun `when refresh is true, getCompendiumList fetches entries from network`() = runTest {
        compendiumEntryDao.entriesToReturn = localCompendiumEntries
        hyruleCompendiumService.entriesResponse = successCompendiumEntriesResponse
        val result = compendiumRepositoryImpl.getCompendiumList(refresh = true)
        assertThat(result).isInstanceOf(Result.Success::class)
        result as Result.Success
        assertThat(result.data).isEqualTo(compendiumEntries)
    }

    @Test
    fun `when there is a network error, getCompendiumList returns an error`() = runTest {
        compendiumEntryDao.entriesToReturn = emptyList()
        hyruleCompendiumService.entriesResponse = ApiResponse.Failure.Exception(Exception())
        val result = compendiumRepositoryImpl.getCompendiumList(refresh = false)
        assertThat(result).isInstanceOf(Result.Error::class)
    }

    @Test
    fun `when local storage is empty, getCategoryList fetches entries from network`() = runTest {
        compendiumEntryDao.entriesToReturn = emptyList()
        hyruleCompendiumService.entriesResponse = successCompendiumEntriesResponse
        val result = compendiumRepositoryImpl.getCategoryList(categoryName = "", refresh = false)
        assertThat(result).isInstanceOf(Result.Success::class)
        result as Result.Success
        assertThat(result.data).isEqualTo(compendiumEntries)
    }

    @Test
    fun `when local storage is not empty, getCategoryList fetches entries from local storage`() =
        runTest {
            compendiumEntryDao.entriesToReturn = localCompendiumEntries
            val result =
                compendiumRepositoryImpl.getCategoryList(categoryName = "", refresh = false)
            assertThat(result).isInstanceOf(Result.Success::class)
            result as Result.Success
            assertThat(result.data).isEqualTo(localCompendiumEntries)
        }

    @Test
    fun `when refresh is true, getCategoryList fetches entries from network`() = runTest {
        compendiumEntryDao.entriesToReturn = localCompendiumEntries
        hyruleCompendiumService.entriesResponse = successCompendiumEntriesResponse
        val result = compendiumRepositoryImpl.getCategoryList(categoryName = "", refresh = true)
        assertThat(result).isInstanceOf(Result.Success::class)
        result as Result.Success
        assertThat(result.data).isEqualTo(compendiumEntries)
    }

    @Test
    fun `when there is a network error, getCategoryList returns an error`() = runTest {
        compendiumEntryDao.entriesToReturn = emptyList()
        hyruleCompendiumService.entriesResponse = ApiResponse.Failure.Exception(Exception())
        val result = compendiumRepositoryImpl.getCategoryList(categoryName = "", refresh = false)
        assertThat(result).isInstanceOf(Result.Error::class)
    }

    @Test
    fun `when local storage is empty, getCompendiumEntry fetches entry from network`() = runTest {
        compendiumEntryDao.entryToReturn = null
        hyruleCompendiumService.entryResponse = successCompendiumEntryResponse
        val result = compendiumRepositoryImpl.getCompendiumEntry(entryId = monsterEntry.id)
        assertThat(result).isInstanceOf(Result.Success::class)
        result as Result.Success
        assertThat(result.data).isEqualTo(monsterEntry)
    }

    @Test
    fun `when local storage is not empty, getCompendiumEntry fetches entry from local storage`() =
        runTest {
            compendiumEntryDao.entryToReturn = localMonsterEntry
            val result =
                compendiumRepositoryImpl.getCompendiumEntry(entryId = localMonsterEntry.id)
            assertThat(result).isInstanceOf(Result.Success::class)
            result as Result.Success
            assertThat(result.data).isEqualTo(localMonsterEntry)
        }

    @Test
    fun `when there is a network error, getCompendiumEntry returns an error`() = runTest {
        compendiumEntryDao.entryToReturn = null
        hyruleCompendiumService.entryResponse = ApiResponse.Failure.Exception(Exception())
        val result = compendiumRepositoryImpl.getCompendiumEntry(entryId = monsterEntry.id)
        assertThat(result).isInstanceOf(Result.Error::class)
    }
}
