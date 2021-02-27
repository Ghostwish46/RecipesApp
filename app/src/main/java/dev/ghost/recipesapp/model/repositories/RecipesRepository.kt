package dev.ghost.recipesapp.model.repositories

import androidx.lifecycle.LiveData
import dev.ghost.recipesapp.model.db.RecipeImagesDao
import dev.ghost.recipesapp.model.db.RecipeSimilarDao
import dev.ghost.recipesapp.model.db.RecipesDao
import dev.ghost.recipesapp.model.domain.RecipeResult
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeSimilar
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.model.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(
    private val recipesDao: RecipesDao,
    private val recipeImagesDao: RecipeImagesDao,
    private val recipeSimilarDao: RecipeSimilarDao,
    private val apiService: ApiService
) {

    var data: LiveData<List<RecipeWithImages>> = recipesDao.getRecipes()

    fun getRecipeWithSimilar(uuid: String) = recipesDao.getRecipeByUUID(uuid)

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val response = apiService.getRecipesAsync()
                .await()

            response.recipes.forEach { recipe ->
                val currentRecipe = mapRecipeToDb(recipe)
                recipesDao.insert(currentRecipe)

                addRecipeImages(recipe)
            }
        }
    }

    suspend fun refreshRecipeDetails(uuid: String) {
        withContext(Dispatchers.IO) {
            val response = apiService.getRecipeDetailsAsync(uuid)
                .await()
            val currentRecipe = mapRecipeToDb(response.recipe)
            recipesDao.insert(currentRecipe)

            addRecipeImages(response.recipe)
            addSimilarRecipes(response.recipe)
        }
    }

    private fun addRecipeImages(recipeResult: RecipeResult) {
        recipeResult.images.forEach { imagePath ->
            recipeImagesDao.insert(RecipeImage(recipeResult.uuid.toString(), imagePath))
        }
    }

    private fun addSimilarRecipes(recipeResult: RecipeResult) {
        recipeResult.similar?.let {
            it.forEach { similarResult ->
                if (recipesDao.isRecipeExists(similarResult.uuid.toString()) == 0)
                    recipesDao.insert(Recipe(similarResult.uuid.toString(), similarResult.name))

                recipeSimilarDao.insert(
                    RecipeSimilar(
                        recipeResult.uuid.toString(),
                        similarResult.uuid.toString()
                    )
                )
            }
        }
    }

    private fun mapRecipeToDb(recipeResult: RecipeResult): Recipe {
        return Recipe(recipeResult.uuid.toString(), recipeResult.name).apply {
            lastUpdated = recipeResult.lastUpdated
            description = recipeResult.description ?: ""
            instructions = recipeResult.instructions
            difficulty = recipeResult.difficulty
        }
    }
}