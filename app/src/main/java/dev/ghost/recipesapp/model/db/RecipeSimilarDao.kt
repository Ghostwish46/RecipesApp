package dev.ghost.recipesapp.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeSimilar

@Dao
interface RecipeSimilarDao {
    @Query("SELECT * FROM recipeSimilar")
    fun getData(): LiveData<RecipeSimilar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipeSimilar: RecipeSimilar)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(recipeSimilar: RecipeSimilar)

    @Delete
    fun delete(recipeSimilar: RecipeSimilar)
}