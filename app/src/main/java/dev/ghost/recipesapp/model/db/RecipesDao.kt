package dev.ghost.recipesapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.model.entities.RecipeWithImagesAndSimilar
import java.util.concurrent.Future

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): LiveData<List<RecipeWithImages>>

    @Query("SELECT * FROM recipes where LOWER(name) LIKE :searchWord or LOWER(description) LIKE :searchWord or LOWER(instructions) LIKE :searchWord order by name")
    fun getRecipesByFilters(searchWord:String): LiveData<List<RecipeWithImages>>

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