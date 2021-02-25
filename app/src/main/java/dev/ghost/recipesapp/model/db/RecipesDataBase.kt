package dev.ghost.recipesapp.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeSimilar

@Database(entities = [Recipe::class, RecipeImage::class, RecipeSimilar::class], version = 1)
abstract class RecipesDataBase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
    abstract fun recipeImagesDao(): RecipeImagesDao
    abstract fun recipeSimilarDao(): RecipeSimilarDao
}