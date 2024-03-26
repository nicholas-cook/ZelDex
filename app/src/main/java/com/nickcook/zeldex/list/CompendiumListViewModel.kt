package com.nickcook.zeldex.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickcook.zeldex.NavigationEvent
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompendiumListViewModel @Inject constructor(
    private val compendiumRepository: CompendiumRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _category: CompendiumCategory = CompendiumCategory.fromCategoryName(
        savedStateHandle["categoryName"] ?: CompendiumCategory.ALL.categoryName
    )

    private val _searchQuery = MutableStateFlow("")
    private val _refreshingState = MutableStateFlow(true)
    private val _listFlow =
        MutableStateFlow<Result<List<CompendiumEntry>>>(Result.Success(emptyList()))

    private val _screenState = combine(
        _searchQuery,
        _listFlow,
        _refreshingState
    ) { query, compendiumListResult, isRefreshing ->
        if (isRefreshing) {
            return@combine CompendiumListScreenState.Loading(_category)
        }
        val compendiumList = when (compendiumListResult) {
            is Result.Success -> {
                compendiumListResult.data
            }

            else -> return@combine CompendiumListScreenState.Error(_category)
        }
        if (query.isBlank()) {
            CompendiumListScreenState.Success(_category, compendiumList)
        } else {
            val filteredList = compendiumList.filter { it.name.contains(query, ignoreCase = true) }
            CompendiumListScreenState.Success(_category, filteredList)
        }
    }.stateIn(
        viewModelScope,
        WhileSubscribed(5000),
        CompendiumListScreenState.Loading(_category)
    )
    val screenState = _screenState

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent = _navigationEvent.asStateFlow()

    init {
        viewModelScope.launch {
            _refreshingState.value = true
            val result = when (_category) {
                CompendiumCategory.ALL -> compendiumRepository.getCompendiumList()
                else -> compendiumRepository.getCategoryList(_category.categoryName)
            }
            _listFlow.value = result
            _refreshingState.value = false
        }
    }

    fun searchEntries(query: String) {
        _searchQuery.value = query
    }

    fun refreshList() {
        viewModelScope.launch {
            _refreshingState.value = true
            val result = when (_category) {
                CompendiumCategory.ALL -> compendiumRepository.getCompendiumList(refresh = true)
                else -> compendiumRepository.getCategoryList(
                    categoryName = _category.categoryName,
                    refresh = true
                )
            }
            _listFlow.value = result
            _refreshingState.value = false
        }
    }

    fun onEntryClicked(entryId: Int) {
        _navigationEvent.value = NavigationEvent.NavigateToEntry(entryId)
    }

    fun onBackClicked() {
        _navigationEvent.value = NavigationEvent.NavigateBack
    }

    fun onNavigated() {
        _navigationEvent.value = null
    }
}
