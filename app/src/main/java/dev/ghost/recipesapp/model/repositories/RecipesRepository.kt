package dev.ghost.recipesapp.model.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import dev.ghost.recipesapp.model.db.RecipeImagesDao
import dev.ghost.recipesapp.model.db.RecipesDao
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.model.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(
    private val recipesDao: RecipesDao,
    private val recipeImagesDao: RecipeImagesDao,
    private val apiService: ApiService
) {

    var data: LiveData<List<RecipeWithImages>> = recipesDao.getData()

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            val response = apiService.getRecipesAsync()
                .await()

            response.recipes.forEach { recipe ->
                val currentRecipe = Recipe(recipe.uuid.toString(), recipe.name).apply {
                    lastUpdated = recipe.lastUpdated
                    description = recipe.description ?: ""
                    instructions = recipe.instructions
                    difficulty = recipe.difficulty
                }
                recipesDao.insert(currentRecipe)

                recipe.images.forEach { imagePath ->
                    recipeImagesDao.insert(RecipeImage(0, currentRecipe.uuid, imagePath))
                }
            }
        }
    }
}