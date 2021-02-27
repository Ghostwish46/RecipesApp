package dev.ghost.recipesapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.model.entities.RecipeWithImagesAndSimilar

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): LiveData<List<RecipeWithImages>>

    @Transaction
    @Query("SELECT * FROM recipes where uuid = :uuid")
    fun getRecipeByUUID(uuid:String): LiveData<RecipeWithImagesAndSimilar>

    @Query("SELECT * FROM recipes where uuid = :uuid")
    fun isRecipeExists(uuid: String):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}