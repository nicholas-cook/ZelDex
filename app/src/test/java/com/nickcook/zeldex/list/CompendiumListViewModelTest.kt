package com.nickcook.zeldex.list

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import com.nickcook.zeldex.NavigationEvent
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.testing.data.creatureEntry
import com.nickcook.zeldex.core.testing.data.equipmentEntry
import com.nickcook.zeldex.core.testing.data.materialEntry
import com.nickcook.zeldex.core.testing.data.monsterEntry
import com.nickcook.zeldex.core.testing.data.treasureEntry
import com.nickcook.zeldex.core.testing.fake.FakeCompendiumRepository
import com.nickcook.zeldex.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.drop
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
    fun `correct entries are returned for creatures category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.CREATURES.categoryName)
        })
        repository.entriesResult = Result.Success(listOf(creatureEntry))
        val creaturesResult = viewModel.screenState.drop(1).first()
        assertThat(creaturesResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val creatureEntries = (creaturesResult as CompendiumListScreenState.Success).listEntries
        assertThat(creatureEntries).isNotEmpty()
        assertThat(creatureEntries.first()).isEqualTo(creatureEntry)
    }

    @Test
    fun `correct entries are returned for monsters category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.MONSTERS.categoryName)
        })
        repository.entriesResult = Result.Success(listOf(monsterEntry))
        val monstersResult = viewModel.screenState.drop(1).first()
        assertThat(monstersResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val monsterEntries = (monstersResult as CompendiumListScreenState.Success).listEntries
        assertThat(monsterEntries).isNotEmpty()
        assertThat(monsterEntries.first()).isEqualTo(monsterEntry)
    }

    @Test
    fun `correct entries are returned for materials category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.MATERIALS.categoryName)
        })
        repository.entriesResult = Result.Success(listOf(materialEntry))
        val materialsResult = viewModel.screenState.drop(1).first()
        assertThat(materialsResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val materialEntries = (materialsResult as CompendiumListScreenState.Success).listEntries
        assertThat(materialEntries).isNotEmpty()
        assertThat(materialEntries.first()).isEqualTo(materialEntry)
    }

    @Test
    fun `correct entries are returned for equipment category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.EQUIPMENT.categoryName)
        })
        repository.entriesResult = Result.Success(listOf(equipmentEntry))
        val equipmentResult = viewModel.screenState.drop(1).first()
        assertThat(equipmentResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val equipmentEntries = (equipmentResult as CompendiumListScreenState.Success).listEntries
        assertThat(equipmentEntries).isNotEmpty()
        assertThat(equipmentEntries.first()).isEqualTo(equipmentEntry)
    }

    @Test
    fun `correct entries are returned for treasure category`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.TREASURE.categoryName)
        })
        repository.entriesResult = Result.Success(listOf(treasureEntry))
        val treasureResult = viewModel.screenState.drop(1).first()
        assertThat(treasureResult).isInstanceOf(CompendiumListScreenState.Success::class)
        val treasureEntries = (treasureResult as CompendiumListScreenState.Success).listEntries
        assertThat(treasureEntries).isNotEmpty()
        assertThat(treasureEntries.first()).isEqualTo(treasureEntry)
    }

    @Test
    fun `error state is returned when repository returns error`() = runTest {
        repository.entriesResult = Result.Error("")
        viewModel = CompendiumListViewModel(repository, SavedStateHandle())
        val result = viewModel.screenState.drop(1).first()
        assertThat(result).isInstanceOf(CompendiumListScreenState.Error::class)
    }

    @Test
    fun `correct entries are returned in the correct order for CompendiumCategory ALL`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })
        val allResult = viewModel.screenState.drop(1).first()
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
        val searchResult = viewModel.screenState.drop(1).first()
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
        val refreshResult = viewModel.screenState.drop(1).first()
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
        repository.entriesResult = Result.Error("")
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })

        viewModel.refreshList()
        val refreshResult = viewModel.screenState.drop(1).first()
        assertThat(refreshResult).isInstanceOf(CompendiumListScreenState.Error::class)
    }

    @Test
    fun `back navigation event is emitted when onBackClicked is called`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })
        viewModel.onBackClicked()
        val result = viewModel.navigationEvent.first()
        assertThat(result).isEqualTo(NavigationEvent.NavigateBack)
    }

    @Test
    fun `navigation event is reset when onNavigated is called`() = runTest {
        viewModel = CompendiumListViewModel(repository, SavedStateHandle().apply {
            set("categoryName", CompendiumCategory.ALL.categoryName)
        })
        viewModel.onBackClicked()
        var result = viewModel.navigationEvent.first()
        assertThat(result).isEqualTo(NavigationEvent.NavigateBack)
        viewModel.onNavigated()
        result = viewModel.navigationEvent.first()
        assertThat(result).isEqualTo(null)
    }
}
