package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipeImages")
class RecipeImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val recipeUUID: UUID,
    val path: String
) {
}