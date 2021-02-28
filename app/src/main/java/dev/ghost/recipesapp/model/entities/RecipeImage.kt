package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipeImages", primaryKeys = ["recipeUUID", "path"])
data class RecipeImage(
    val recipeUUID: String,
    val path: String
) {
}