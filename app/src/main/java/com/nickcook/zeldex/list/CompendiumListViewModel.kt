package com.nickcook.zeldex.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.data.model.Result
import com.nickcook.zeldex.core.data.repository.CompendiumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _screenState =
        MutableStateFlow<CompendiumListScreenState>(CompendiumListScreenState.Loading(_category))
    val screenState = _screenState.asStateFlow()

    private var _fullList = listOf<CompendiumEntry>()

    init {
        viewModelScope.launch {
            val result = when (_category) {
                CompendiumCategory.ALL -> compendiumRepository.getCompendiumList()
                else -> compendiumRepository.getCategoryList(_category.categoryName)
            }
            if (result is Result.Success) {
                _fullList = result.data
                _screenState.value = CompendiumListScreenState.Success(_category, result.data)
            } else {
                _screenState.value = CompendiumListScreenState.Error(_category)
            }
        }
    }

    fun searchEntries(query: String) {
        viewModelScope.launch {
            if (_screenState.value is CompendiumListScreenState.Success) {
                val filteredList =
                    _fullList.filter { it.name.contains(query, ignoreCase = true) }
                _screenState.value = CompendiumListScreenState.Success(_category, filteredList)
            }
        }
    }

    fun refreshList() {
        viewModelScope.launch {
            _screenState.value = CompendiumListScreenState.Loading(_category)
            val result = when (_category) {
                CompendiumCategory.ALL -> compendiumRepository.getCompendiumList(refresh = true)
                else -> compendiumRepository.getCategoryList(
                    categoryName = _category.categoryName,
                    refresh = true
                )
            }
            if (result is Result.Success) {
                _fullList = result.data
                _screenState.value = CompendiumListScreenState.Success(_category, result.data)
            } else {
                _screenState.value = CompendiumListScreenState.Error(_category)
            }
        }
    }
}