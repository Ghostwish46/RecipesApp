package dev.ghost.recipesapp.presentation.recipe_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.ghost.recipesapp.RecipesApplication
import dev.ghost.recipesapp.model.db.RecipeWithImagesAndSimilar
import dev.ghost.recipesapp.data.remote.LoadingState
import dev.ghost.recipesapp.model.db.RecipeWithImages
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import dev.ghost.recipesapp.presentation.recipe_images.RecipeImagesAdapter
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    private val loadingState = MutableLiveData<LoadingState>()

    lateinit var similarRecipesAdapter: SimilarRecipesAdapter
    lateinit var recipeImagesAdapter: RecipeImagesAdapter

    init {
        (application as RecipesApplication).appComponent.inject(this)
    }

    fun getLoadingState() = loadingState
    fun getRecipeWithSimilar(uuid: String): LiveData<RecipeWithImagesAndSimilar> {
        fetchRecipeDetails(uuid)
        return recipesRepository.getRecipeFullInfoByUUID(uuid)
    }

    private fun fetchRecipeDetails(uuid: String) {
        viewModelScope.launch {
            try {
                loadingState.value = LoadingState.LOADING
                recipesRepository.refreshRecipeDetails(uuid)
                loadingState.value = LoadingState.LOADED
            } catch (ex: Exception) {
                loadingState.value = LoadingState.error(ex.message)
            }
        }
    }

    fun fetchSimilarRecipesInfo(similarRecipes: List<RecipeWithImages>) {
        viewModelScope.launch {
            try {
                loadingState.value = LoadingState.LOADING
                for (recipeWithImages in similarRecipes)
                    recipesRepository.refreshRecipeDetails(recipeWithImages.recipe.uuid)
                loadingState.value = LoadingState.LOADED
            } catch (ex: Exception) {
                loadingState.value = LoadingState.error(ex.message)
            }
        }
    }
}