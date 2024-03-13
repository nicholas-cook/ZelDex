package com.nickcook.zeldex.entry

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.nickcook.zeldex.core.testing.data.materialEntry
import com.nickcook.zeldex.core.testing.fake.FakeCompendiumRepository
import com.nickcook.zeldex.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CompendiumEntryViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CompendiumEntryViewModel
    private val repository = FakeCompendiumRepository()

    @Test
    fun `correct entry is returned for the given entryId`() = runTest {
        viewModel = CompendiumEntryViewModel(repository, SavedStateHandle().apply {
            set("entryId", "2")
        })
        val result = viewModel.screenState.first()
        assertThat(result).isInstanceOf(CompendiumEntryScreenState.Success::class)
        val entry = (result as CompendiumEntryScreenState.Success).entry
        assertThat(entry).isEqualTo(materialEntry)
    }

    @Test
    fun `error state is returned when repository returns an error`() = runTest {
        repository.isErrorResponse = true
        viewModel = CompendiumEntryViewModel(repository, SavedStateHandle().apply {
            set("entryId", "2")
        })
        val result = viewModel.screenState.first()
        assertThat(result).isInstanceOf(CompendiumEntryScreenState.Error::class)
    }
}
