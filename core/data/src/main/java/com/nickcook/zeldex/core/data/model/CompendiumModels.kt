package com.nickcook.zeldex.core.data.model

import com.nickcook.zeldex.core.localstorage.room.entity.CompendiumEntryEntity
import com.nickcook.zeldex.core.network.model.CompendiumItemResponse

data class CompendiumEntry(
    val name: String,
    val id: Int,
    val category: CompendiumCategory,
    val description: String,
    val image: String,
    val commonLocations: List<String>,
    val dlc: Boolean,
    val drops: List<String>,
    val properties: PropertiesEntry?,
    val heartsRecovered: Float?,
    val cookingEffect: String
) {
    companion object {
        fun fromCompendiumItemResponse(response: CompendiumItemResponse): CompendiumEntry {
            return CompendiumEntry(
                name = response.name,
                id = response.id,
                category = CompendiumCategory.fromCategoryName(response.category),
                description = response.description,
                image = response.image,
                commonLocations = response.commonLocations ?: emptyList(),
                dlc = response.dlc,
                drops = response.drops ?: emptyList(),
                properties = response.properties?.let { properties ->
                    if (properties.attack == null && properties.defense == null) {
                        null
                    } else {
                        PropertiesEntry(
                            attack = properties.attack ?: 0,
                            defense = properties.defense ?: 0
                        )
                    }
                },
                heartsRecovered = response.heartsRecovered,
                cookingEffect = response.cookingEffect ?: ""
            )

        }

        fun fromCompendiumEntryEntity(entity: CompendiumEntryEntity): CompendiumEntry {
            return CompendiumEntry(
                name = entity.name,
                id = entity.id,
                category = CompendiumCategory.fromCategoryName(entity.category),
                description = entity.description,
                image = entity.image,
                commonLocations = if (entity.commonLocations.isEmpty()) {
                    emptyList()
                } else {
                    entity.commonLocations.split("*|*")
                },
                dlc = entity.dlc,
                drops = if (entity.drops.isEmpty()) {
                    emptyList()
                } else {
                    entity.drops.split("*|*")
                },
                properties = entity.properties?.let { properties ->
                    val (attack, defense) = properties.split("*|*").map { it.toInt() }
                    PropertiesEntry(attack, defense)
                },
                heartsRecovered = entity.heartsRecovered,
                cookingEffect = entity.cookingEffect
            )
        }

        fun CompendiumEntry.toCompendiumEntryEntity(): CompendiumEntryEntity {
            return CompendiumEntryEntity(
                name = name,
                id = id,
                category = category.categoryName,
                description = description,
                image = image,
                commonLocations = commonLocations.joinToString("*|*"),
                dlc = dlc,
                drops = drops.joinToString("*|*"),
                properties = properties?.let { "${it.attack}*|*${it.defense}" },
                heartsRecovered = heartsRecovered,
                cookingEffect = cookingEffect
            )
        }
    }
}

data class PropertiesEntry(
    val attack: Int,
    val defense: Int
)
