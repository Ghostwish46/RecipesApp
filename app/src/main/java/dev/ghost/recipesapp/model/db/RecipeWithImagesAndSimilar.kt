package dev.ghost.recipesapp.model.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RecipeWithImagesAndSimilar(
    @Embedded
    val recipe: Recipe,
    @Relation(
        entity = RecipeImage::class,
        entityColumn = "recipeUUID",
        parentColumn = "uuid"
    )
    val images: List<RecipeImage>,

    @Relation(
        entity = Recipe::class,
        entityColumn = "uuid",
        parentColumn = "uuid",
        associateBy = Junction(
            value = RecipeSimilar::class,
            entityColumn = "similar",
            parentColumn = "originalUUID",
        )
    )
    val similarRecipes: List<RecipeWithImages>
)