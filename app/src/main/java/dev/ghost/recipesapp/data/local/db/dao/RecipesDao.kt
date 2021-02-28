package dev.ghost.recipesapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.db.Recipe
import dev.ghost.recipesapp.model.db.RecipeWithImages
import dev.ghost.recipesapp.model.db.RecipeWithImagesAndSimilar

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    fun getRecipesWithImages(): LiveData<List<RecipeWithImages>>

    @Query("SELECT * FROM recipes where LOWER(name) LIKE :searchWord or LOWER(description) LIKE :searchWord or LOWER(instructions) LIKE :searchWord order by name")
    fun getRecipesWithImagesByFilters(searchWord: String): LiveData<List<RecipeWithImages>>

    @Transaction
    @Query("SELECT * FROM recipes where uuid = :uuid")
    fun getRecipeFullInfoByUUID(uuid: String): LiveData<RecipeWithImagesAndSimilar>

    @Transaction
    @Query("SELECT * FROM recipes where uuid = :uuid")
    fun getRecipeWithImagesByUUID(uuid: String): LiveData<RecipeWithImages>

    @Query("SELECT * FROM recipes where uuid = :uuid")
    fun isRecipeExists(uuid: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}