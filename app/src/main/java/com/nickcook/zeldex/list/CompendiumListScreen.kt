package com.nickcook.zeldex.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nickcook.zeldex.R
import com.nickcook.zeldex.components.ErrorState
import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.ui.theme.ZelDexTheme

@Composable
fun CompendiumListRoute(
    onNavigateUp: () -> Unit,
    onItemClicked: (Int) -> Unit,
    viewModel: CompendiumListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    CompendiumListScreen(
        screenState = screenState,
        onItemClicked = onItemClicked,
        onSearch = viewModel::searchEntries,
        onRefresh = viewModel::refreshList,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun CompendiumListScreen(
    screenState: CompendiumListScreenState,
    onItemClicked: (Int) -> Unit,
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(topBar = {
        ListTopBar(
            screenState = screenState,
            onSearch = onSearch,
            onRefresh = onRefresh,
            onNavigateUp = onNavigateUp
        )
    }) { paddingValues ->
        when (screenState) {
            is CompendiumListScreenState.Error -> {
                ErrorState()
            }

            is CompendiumListScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CompendiumListScreenState.Success -> {
                val listEntries = screenState.listEntries
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    val firstCreatureIndex =
                        listEntries.indexOfFirst { it.category == CompendiumCategory.CREATURES }
                    val firstMonsterIndex =
                        listEntries.indexOfFirst { it.category == CompendiumCategory.MONSTERS }
                    val firstMaterialIndex =
                        listEntries.indexOfFirst { it.category == CompendiumCategory.MATERIALS }
                    val firstEquipmentIndex =
                        listEntries.indexOfFirst { it.category == CompendiumCategory.EQUIPMENT }
                    val firstTreasureIndex =
                        listEntries.indexOfFirst { it.category == CompendiumCategory.TREASURE }
                    itemsIndexed(listEntries) { index, listEntry ->
                        if (screenState.category == CompendiumCategory.ALL) {
                            when (index) {
                                firstCreatureIndex -> CompendiumListCategoryItem(
                                    stringResource(
                                        id = R.string.category_creatures
                                    )
                                )

                                firstMonsterIndex -> CompendiumListCategoryItem(
                                    stringResource(
                                        id = R.string.category_monsters
                                    )
                                )

                                firstMaterialIndex -> CompendiumListCategoryItem(
                                    stringResource(
                                        id = R.string.category_materials
                                    )
                                )

                                firstEquipmentIndex -> CompendiumListCategoryItem(
                                    stringResource(
                                        id = R.string.category_equipment
                                    )
                                )

                                firstTreasureIndex -> CompendiumListCategoryItem(
                                    stringResource(
                                        id = R.string.category_treasure
                                    )
                                )
                            }
                        }
                        CompendiumListItem(entry = listEntry, onItemClicked = onItemClicked)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTopBar(
    screenState: CompendiumListScreenState,
    onSearch: (String) -> Unit,
    onRefresh: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        var searchVisible by rememberSaveable { mutableStateOf(false) }
        val title = when (screenState.category) {
            CompendiumCategory.ALL -> stringResource(id = R.string.title_category_all)
            CompendiumCategory.CREATURES -> stringResource(id = R.string.category_creatures)
            CompendiumCategory.MONSTERS -> stringResource(id = R.string.category_monsters)
            CompendiumCategory.EQUIPMENT -> stringResource(id = R.string.category_equipment)
            CompendiumCategory.MATERIALS -> stringResource(id = R.string.category_materials)
            CompendiumCategory.TREASURE -> stringResource(id = R.string.category_treasure)
        }
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            navigationIcon = {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.cd_nav_back)
                    )
                }
            },
            actions = {
                if (screenState is CompendiumListScreenState.Success) {
                    if (!searchVisible) {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null
                            )
                        }
                    }
                    IconButton(onClick = {
                        searchVisible = !searchVisible
                        onSearch("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (searchVisible) {
            var searchQuery by rememberSaveable { mutableStateOf("") }
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    onSearch(it)
                },
                onSearch = onSearch,
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            onSearch("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }
                },
                content = {})
        }
    }
}

@Composable
fun CompendiumListItem(entry: CompendiumEntry, onItemClicked: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = { onItemClicked(entry.id) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val phErrorPainter = painterResource(
            id = when (entry.category) {
                CompendiumCategory.MONSTERS -> R.drawable.ph_monsters
                CompendiumCategory.CREATURES -> R.drawable.ph_creatures
                CompendiumCategory.EQUIPMENT -> R.drawable.ph_equipment
                CompendiumCategory.MATERIALS -> R.drawable.ph_materials
                else -> R.drawable.ph_treasure
            }
        )
        AsyncImage(
            model = entry.image,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 32.dp),
            placeholder = phErrorPainter,
            error = phErrorPainter
        )
        Column {
            Text(text = entry.name.uppercase(), style = MaterialTheme.typography.titleSmall)
            Text(
                text = stringResource(id = R.string.entry_number, entry.id),
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
            )
        }
    }
}

@Composable
fun CompendiumListCategoryItem(categoryName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        Text(
            text = categoryName.uppercase(),
            style = MaterialTheme.typography.headlineSmall,
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CompendiumListScreenPreview() {
    val compendiumEntries = listOf(
        CompendiumEntry(
            name = "Bokoblin",
            id = 1,
            category = CompendiumCategory.MONSTERS,
            description = "A monster that lives in the forest. It's not very strong, but its numbers make it a threat.",
            image = "https://botw-compendium.herokuapp.com/api/v2/entry/bokoblin/image",
            commonLocations = listOf("Hyrule Field", "Faron Grasslands"),
            dlc = false,
            drops = listOf("Bokoblin Horn", "Bokoblin Fang"),
            properties = null,
            heartsRecovered = null,
            cookingEffect = ""
        ),
        CompendiumEntry(
            name = "Apple",
            id = 1,
            category = CompendiumCategory.MATERIALS,
            description = "A common fruit found on trees all around Hyrule. Eat it fresh, or cook it to increase its effect.",
            image = "https://botw-compendium.herokuapp.com/api/v2/entry/apple/image",
            commonLocations = listOf("Hyrule Field", "Faron Grasslands"),
            dlc = false,
            drops = emptyList(),
            properties = null,
            heartsRecovered = 0.5f,
            cookingEffect = "Low-level health recovery"
        )
    )
    ZelDexTheme {
        CompendiumListScreen(
            screenState = CompendiumListScreenState.Success(
                CompendiumCategory.ALL,
                compendiumEntries
            ),
            onItemClicked = {},
            onSearch = {},
            onRefresh = {},
            onNavigateUp = {}
        )
    }
}