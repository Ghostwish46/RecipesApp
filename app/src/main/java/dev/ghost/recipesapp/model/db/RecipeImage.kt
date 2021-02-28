package dev.ghost.recipesapp.model.db

import androidx.room.Entity

@Entity(tableName = "recipeImages", primaryKeys = ["recipeUUID", "path"])
data class RecipeImage(
    val recipeUUID: String,
    val path: String
) {
}