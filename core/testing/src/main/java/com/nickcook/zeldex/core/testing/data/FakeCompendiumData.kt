package com.nickcook.zeldex.core.testing.data

import com.nickcook.zeldex.core.data.model.CompendiumCategory
import com.nickcook.zeldex.core.data.model.CompendiumEntry
import com.nickcook.zeldex.core.network.model.CompendiumEntryResponse
import com.nickcook.zeldex.core.network.model.CompendiumItemResponse
import com.nickcook.zeldex.core.network.model.CompendiumListResponse
import com.nickcook.zeldex.core.network.model.PropertiesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response

val monsterEntry = CompendiumEntry(
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
)

val materialEntry = CompendiumEntry(
    name = "Apple",
    id = 2,
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

val equipmentEntry = CompendiumEntry(
    name = "Ancient Battle Axe",
    id = 3,
    category = CompendiumCategory.EQUIPMENT,
    description = "A weapon used by Guardian Scouts. Its unique blade was forged using ancient technology.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/ancient_battle_axe/image",
    commonLocations = emptyList(),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val creatureEntry = CompendiumEntry(
    name = "Blue-Winged Heron",
    id = 4,
    category = CompendiumCategory.CREATURES,
    description = "A bird that can be found near the water. It's not especially rare, but it's a bit difficult to catch.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/blue_winged_heron/image",
    commonLocations = listOf("Hyrule Ridge", "Lanayru Wetlands"),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val treasureEntry = CompendiumEntry(
    name = "Amber",
    id = 5,
    category = CompendiumCategory.TREASURE,
    description = "A fossilized resin with a caramelesque sheen to it. It's been valued as a component in decorations and crafting since ancient times.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/amber/image",
    commonLocations = listOf("Hyrule Ridge", "Lanayru Wetlands"),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val compendiumEntries = listOf(
    monsterEntry,
    materialEntry,
    equipmentEntry,
    creatureEntry,
    treasureEntry
)

val localMonsterEntry = CompendiumEntry(
    name = "Local Bokoblin",
    id = 6,
    category = CompendiumCategory.MONSTERS,
    description = "A monster that lives in the forest. It's not very strong, but its numbers make it a threat.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/bokoblin/image",
    commonLocations = listOf("Hyrule Field", "Faron Grasslands"),
    dlc = false,
    drops = listOf("Bokoblin Horn", "Bokoblin Fang"),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val localMaterialEntry = CompendiumEntry(
    name = "Local Apple",
    id = 7,
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

val localEquipmentEntry = CompendiumEntry(
    name = "Local Ancient Battle Axe",
    id = 8,
    category = CompendiumCategory.EQUIPMENT,
    description = "A weapon used by Guardian Scouts. Its unique blade was forged using ancient technology.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/ancient_battle_axe/image",
    commonLocations = emptyList(),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val localCreatureEntry = CompendiumEntry(
    name = "Local Blue-Winged Heron",
    id = 9,
    category = CompendiumCategory.CREATURES,
    description = "A bird that can be found near the water. It's not especially rare, but it's a bit difficult to catch.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/blue_winged_heron/image",
    commonLocations = listOf("Hyrule Ridge", "Lanayru Wetlands"),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val localTreasureEntry = CompendiumEntry(
    name = "Local Amber",
    id = 10,
    category = CompendiumCategory.TREASURE,
    description = "A fossilized resin with a caramelesque sheen to it. It's been valued as a component in decorations and crafting since ancient times.",
    image = "https://botw-compendium.herokuapp.com/api/v2/entry/amber/image",
    commonLocations = listOf("Hyrule Ridge", "Lanayru Wetlands"),
    dlc = false,
    drops = emptyList(),
    properties = null,
    heartsRecovered = null,
    cookingEffect = ""
)

val localCompendiumEntries = listOf(
    localMonsterEntry,
    localMaterialEntry,
    localEquipmentEntry,
    localCreatureEntry,
    localTreasureEntry
)

val successCompendiumEntriesResponse =
    ApiResponse.Success(Response.success(CompendiumListResponse(compendiumEntries.map { it.toCompendiumItemResponse() })))
val successCompendiumEntryResponse =
    ApiResponse.Success(Response.success(CompendiumEntryResponse(monsterEntry.toCompendiumItemResponse())))

fun CompendiumEntry.toCompendiumItemResponse() = CompendiumItemResponse(
    name = name,
    id = id,
    category = category.categoryName,
    description = description,
    image = image,
    commonLocations = commonLocations,
    dlc = dlc,
    drops = drops,
    properties = properties?.let { properties ->
        PropertiesResponse(
            attack = properties.attack,
            defense = properties.defense
        )
    },
    heartsRecovered = heartsRecovered,
    cookingEffect = cookingEffect
)
