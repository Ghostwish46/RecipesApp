package dev.ghost.recipesapp.model.api

import java.util.*

data class RecipeResult(
    val uuid: UUID,
    val name: String,
    val images: List<String>,
    val lastUpdated: Long,
    val description: String?,
    val instructions: String,
    val difficulty: Int,
    val similar: List<SimilarResult>?,
)

