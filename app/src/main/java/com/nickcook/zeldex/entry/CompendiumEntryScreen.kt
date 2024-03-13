package com.nickcook.zeldex.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.nickcook.zeldex.core.data.model.PropertiesEntry
import com.nickcook.zeldex.ui.theme.ZelDexTheme
import java.util.Locale
import kotlin.math.ceil

@Composable
fun CompendiumEntryRoute(
    onNavigateUp: () -> Unit,
    viewModel: CompendiumEntryViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    CompendiumEntryScreen(screenState = screenState, onNavigateUp = onNavigateUp)
}

@Composable
fun CompendiumEntryScreen(screenState: CompendiumEntryScreenState, onNavigateUp: () -> Unit) {
    Scaffold(topBar = {
        EntryTopBar(onNavigateUp = onNavigateUp)
    }) { paddingValues ->
        when (screenState) {
            is CompendiumEntryScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CompendiumEntryScreenState.Error -> {
                ErrorState()
            }

            is CompendiumEntryScreenState.Success -> {
                val entry = screenState.entry
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                    Text(
                        text = stringResource(
                            id = R.string.entry_title,
                            entry.id,
                            entry.name.uppercase()
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    AsyncImage(
                        model = entry.image,
                        contentDescription = stringResource(
                            id = R.string.cd_entry_image,
                            entry.name
                        ),
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 24.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = phErrorPainter,
                        error = phErrorPainter
                    )
                    Text(
                        text = entry.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    if (entry.commonLocations.isNotEmpty()) {
                        LocationsSection(commonLocations = entry.commonLocations)
                        HorizontalDivider()
                    }
                    if (entry.drops.isNotEmpty()) {
                        DropsSection(drops = entry.drops)
                        HorizontalDivider()
                    }
                    entry.properties?.let {
                        PropertiesSection(propertiesEntry = it)
                        HorizontalDivider()
                    }
                    entry.heartsRecovered?.let { heartsRecovered ->
                        if (heartsRecovered > 0f) {
                            HeartsRecoveredSection(heartsRecovered = heartsRecovered)
                            HorizontalDivider()
                        }
                    }
                    if (entry.cookingEffect.isNotEmpty()) {
                        CookingEffectSection(cookingEffect = entry.cookingEffect)
                        HorizontalDivider()
                    }
                    if (entry.dlc) {
                        Text(
                            text = stringResource(id = R.string.info_dlc),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationsSection(commonLocations: List<String>) {
    Row {
        Text(
            text = stringResource(id = R.string.title_common_locations),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
    commonLocations.forEach {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
private fun DropsSection(drops: List<String>) {
    Text(
        text = stringResource(id = R.string.title_droppable_items),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    drops.forEach { drop ->
        Text(
            text = drop.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
private fun PropertiesSection(propertiesEntry: PropertiesEntry) {
    Text(
        text = stringResource(id = R.string.title_properties),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.body_attack, propertiesEntry.attack),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.body_defense, propertiesEntry.defense),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun HeartsRecoveredSection(heartsRecovered: Float) {
    Text(
        text = stringResource(id = R.string.title_hearts_recovered),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
    Text(
        text = stringResource(id = R.string.body_hearts_recovered),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    val roundedHearts = ceil(heartsRecovered).toInt()
    Box {
        Row(modifier = Modifier
            .drawWithContent {
                clipRect(right = size.width * (heartsRecovered / roundedHearts)) {
                    this@drawWithContent.drawContent()
                }
            }
            .padding(bottom = 8.dp)) {
            repeat(roundedHearts) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
        Row {
            repeat(roundedHearts) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
private fun CookingEffectSection(cookingEffect: String) {
    Text(
        text = stringResource(id = R.string.title_cooking_effect),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = cookingEffect.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        },
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EntryTopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_compendium_entry),
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
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CompendiumEntryScreenPreview() {
    ZelDexTheme {
        CompendiumEntryScreen(
            screenState = CompendiumEntryScreenState.Success(
                CompendiumEntry(
                    name = "Bokoblin",
                    id = 1,
                    category = CompendiumCategory.MONSTERS,
                    description = "A monster that lives in the forest. It's not very strong, but its numbers make it a threat.",
                    image = "image1",
                    commonLocations = listOf("Hyrule Field", "West Necluda"),
                    drops = listOf("Bokoblin Horn", "Bokoblin Fang", "Bokoblin Guts"),
                    dlc = true,
                    properties = PropertiesEntry(attack = 2, defense = 1),
                    heartsRecovered = 1.5f,
                    cookingEffect = "Increased speed"
                )
            ),
            onNavigateUp = {}
        )
    }
}