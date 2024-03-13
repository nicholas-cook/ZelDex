package com.nickcook.zeldex.core.data.model

enum class CompendiumCategory(val categoryName: String) {
    CREATURES("creatures"),
    MONSTERS("monsters"),
    MATERIALS("materials"),
    EQUIPMENT("equipment"),
    TREASURE("treasure"),
    ALL("all");

    companion object {
        fun fromCategoryName(categoryName: String): CompendiumCategory {
            return entries.first { it.categoryName == categoryName }
        }
    }
}