package dev.ghost.recipesapp.model.repositories

import dev.ghost.recipesapp.data.local.db.dao.RecipeImagesDao
import dev.ghost.recipesapp.data.local.db.dao.RecipeSimilarDao
import dev.ghost.recipesapp.data.local.db.dao.RecipesDao
import dev.ghost.recipesapp.model.api.RecipeResult
import dev.ghost.recipesapp.model.db.Recipe
import dev.ghost.recipesapp.model.db.RecipeImage
import dev.ghost.recipesapp.model.db.RecipeSimilar
import dev.ghost.recipesapp.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(
    private val recipesDao: RecipesDao,
    private val recipeImagesDao: RecipeImagesDao,
    private val recipeSimilarDao: RecipeSimilarDao,
    private val apiService: ApiService
) {

    fun getRecipeFullInfoByUUID(uuid: String) = recipesDao.getRecipeFullInfoByUUID(uuid)

    fun getRecipesByFilters(searchWord: String) =
        recipesDao.getRecipesWithImagesByFilters(searchWord)

    fun getRecipeWithImagesByUUID(uuid: String) = recipesDao.getRecipeWithImagesByUUID(uuid)

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