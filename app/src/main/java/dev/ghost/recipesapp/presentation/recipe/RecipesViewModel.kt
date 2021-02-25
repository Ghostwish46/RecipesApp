package dev.ghost.recipesapp.presentation.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.ghost.recipesapp.App
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.model.network.LoadingState
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class RecipesViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    private val loadingState = MutableLiveData<LoadingState>()
    private val data: LiveData<List<RecipeWithImages>>

    val recipesAdapter: RecipesAdapter = RecipesAdapter()

    init {
        (application as App).appComponent.inject(this)
        data = recipesRepository.data
        fetchData()
    }

    fun getLoadingState() = loadingState
    fun getData() = data

    fun fetchData() {
        viewModelScope.launch {
            try {
                loadingState.value = LoadingState.LOADING
                recipesRepository.refresh()
                loadingState.value = LoadingState.LOADED
            } catch (ex: Exception) {
                loadingState.value = LoadingState.error(ex.message)
            }
        }
    }
}