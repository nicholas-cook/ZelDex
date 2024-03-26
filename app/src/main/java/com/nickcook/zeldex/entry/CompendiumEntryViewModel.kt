package com.nickcook.zeldex.entry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickcook.zeldex.NavigationEvent
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompendiumEntryViewModel @Inject constructor(
    private val compendiumRepository: CompendiumRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _entryId: Int = (savedStateHandle["entryId"] ?: "-1").toInt()

    private val _screenState =
        MutableStateFlow<CompendiumEntryScreenState>(CompendiumEntryScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()

    init {
        getCompendiumEntry()
    }

    fun getCompendiumEntry() {
        viewModelScope.launch {
            val result = compendiumRepository.getCompendiumEntry(_entryId)
            if (result is Result.Success) {
                _screenState.value = CompendiumEntryScreenState.Success(result.data)
            } else {
                _screenState.value = CompendiumEntryScreenState.Error("Failed to fetch data")
            }
        }
    }

    fun onBackClicked() {
        _navigationEvent.value = NavigationEvent.NavigateBack
    }

    fun onNavigated() {
        _navigationEvent.value = null
    }
}