package dev.ghost.recipesapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.db.RecipeImage

@Dao
interface RecipeImagesDao {
    @Query("SELECT * FROM recipeImages")
    fun getData(): LiveData<RecipeImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeImage: RecipeImage)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipeImage: RecipeImage)

    @Delete
    fun delete(recipeImage: RecipeImage)
}