package dev.ghost.recipesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ghost.recipesapp.data.local.db.dao.RecipeImagesDao
import dev.ghost.recipesapp.data.local.db.dao.RecipeSimilarDao
import dev.ghost.recipesapp.data.local.db.dao.RecipesDao
import dev.ghost.recipesapp.model.db.Recipe
import dev.ghost.recipesapp.model.db.RecipeImage
import dev.ghost.recipesapp.model.db.RecipeSimilar

@Database(entities = [Recipe::class, RecipeImage::class, RecipeSimilar::class], version = 1)
abstract class RecipesDataBase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
    abstract fun recipeImagesDao(): RecipeImagesDao
    abstract fun recipeSimilarDao(): RecipeSimilarDao
}