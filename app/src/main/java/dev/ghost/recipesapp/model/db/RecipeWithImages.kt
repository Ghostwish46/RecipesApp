package dev.ghost.recipesapp.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithImages(
    @Embedded
    val recipe: Recipe,
    @Relation(
        entity = RecipeImage::class,
        entityColumn = "recipeUUID",
        parentColumn = "uuid"
    )
    val images: List<RecipeImage>
) {
}