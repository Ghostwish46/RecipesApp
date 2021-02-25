package dev.ghost.recipesapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.entities.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    fun getData(): LiveData<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)
}