package com.nickcook.zeldex.list

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.testing.data.creatureEntry
import com.nickcook.zeldex.core.testing.data.equipmentEntry
import com.nickcook.zeldex.core.testing.data.materialEntry
import com.nickcook.zeldex.core.testing.data.monsterEntry
import com.nickcook.zeldex.core.testing.data.treasureEntry
import com.nickcook.zeldex.core.testing.fake.FakeCompendiumRepository
import com.nickcook.zeldex.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class CompendiumListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CompendiumListViewModel
    private val repository = FakeCompendiumRepository()

    @Test
    fun `correct entries are returned for each category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.CREATURES.categoryName)
        })
        val creaturesResult = viewModel.screenState.first()
        assertThat(creaturesResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val creatureEntries = (creaturesResult as CompendiumListScreenState.Success).listEntries
        assertThat(creatureEntries).isNotEmpty()
        assertThat(creatureEntries.first()).isEqualTo(creatureEntry)

        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.MONSTERS.categoryName)
        })
        val monstersResult = viewModel.screenState.first()
        assertThat(monstersResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val monsterEntries = (monstersResult as CompendiumListScreenState.Success).listEntries
        assertThat(monsterEntries).isNotEmpty()
        assertThat(monsterEntries.first()).isEqualTo(monsterEntry)

        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.MATERIALS.categoryName)
        })
        val materialsResult = viewModel.screenState.first()
        assertThat(materialsResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val materialEntries = (materialsResult as CompendiumListScreenState.Success).listEntries
        assertThat(materialEntries).isNotEmpty()
        assertThat(materialEntries.first()).isEqualTo(materialEntry)

        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.EQUIPMENT.categoryName)
        })
        val equipmentResult = viewModel.screenState.first()
        assertThat(equipmentResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val equipmentEntries = (equipmentResult as CompendiumListScreenState.Success).listEntries
        assertThat(equipmentEntries).isNotEmpty()
        assertThat(equipmentEntries.first()).isEqualTo(equipmentEntry)

        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.TREASURE.categoryName)
        })
        val treasureResult = viewModel.screenState.first()
        assertThat(treasureResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val treasureEntries = (treasureResult as CompendiumListScreenState.Success).listEntries
        assertThat(treasureEntries).isNotEmpty()
        assertThat(treasureEntries.first()).isEqualTo(treasureEntry)
    }

    @Test
    fun `error state is returned when repository returns error`() = runTest {
        repository.isErrorResponse = true
        viewModel = CompendiumListViewModel(repository, SavedStateHandle())
        val result = viewModel.screenState.first()
        assertThat(result).isInstanceOf(CompendiumListScreenState.Error::class)
    }

    @Test
    fun `correct entries are returned in the correct order for CompendiumCategory ALL`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })
        val allResult = viewModel.screenState.first()
        assertThat(allResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val allEntries = (allResult as CompendiumListScreenState.Success).listEntries
        assertThat(allEntries).isNotEmpty()
        assertThat(allEntries).isEqualTo(
            listOf(
                monsterEntry,
                materialEntry,
                equipmentEntry,
                creatureEntry,
                treasureEntry
            )
        )
    }

    @Test
    fun `searching for an entry returns the correct results`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })

        viewModel.searchEntries("Boko")
        val searchResult = viewModel.screenState.first()
        assertThat(searchResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val searchEntries = (searchResult as CompendiumListScreenState.Success).listEntries
        assertThat(searchEntries).isNotEmpty()
        assertThat(searchEntries).isEqualTo(listOf(monsterEntry))
    }

    @Test
    fun `refreshing the list returns the correct entries`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })

        viewModel.refreshList()
        val refreshResult = viewModel.screenState.first()
        assertThat(refreshResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val refreshEntries = (refreshResult as CompendiumListScreenState.Success).listEntries
        assertThat(refreshEntries).isNotEmpty()
        assertThat(refreshEntries).isEqualTo(
            listOf(
                monsterEntry,
                materialEntry,
                equipmentEntry,
                creatureEntry,
                treasureEntry
            )
        )
    }

    @Test
    fun `refreshing the list returns an error when repository returns error`() = runTest {
        repository.isErrorResponse = true
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })

        viewModel.refreshList()
        val refreshResult = viewModel.screenState.first()
        assertThat(refreshResult).isInstanceOf(CompendiumListScreenState.Error::class)
    }
}
