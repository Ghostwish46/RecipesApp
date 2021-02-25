package dev.ghost.recipesapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage

@Dao
interface RecipeImagesDao {
    @Query("SELECT * FROM recipeImages")
    fun getClient(): LiveData<RecipeImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeImage: RecipeImage)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipeImage: RecipeImage)

    @Delete
    fun delete(recipeImage: RecipeImage)
}