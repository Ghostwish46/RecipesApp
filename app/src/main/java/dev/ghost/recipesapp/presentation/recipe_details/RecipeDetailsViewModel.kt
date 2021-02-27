package dev.ghost.recipesapp.presentation.recipe_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.ghost.recipesapp.App
import dev.ghost.recipesapp.model.entities.RecipeWithImagesAndSimilar
import dev.ghost.recipesapp.model.network.LoadingState
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    private val loadingState = MutableLiveData<LoadingState>()

    lateinit var similarRecipesAdapter: SimilarRecipesAdapter

    init {
        (application as App).appComponent.inject(this)
    }

    fun getLoadingState() = loadingState
    fun getRecipeWithSimilar(uuid: String): LiveData<RecipeWithImagesAndSimilar> {
        fetchRecipeDetails(uuid)
        return recipesRepository.getRecipeWithSimilar(uuid)
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
}