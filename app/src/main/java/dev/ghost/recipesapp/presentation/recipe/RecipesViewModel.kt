package dev.ghost.recipesapp.presentation.recipe

import android.app.Application
import androidx.lifecycle.*
import dev.ghost.recipesapp.RecipesApplication
import dev.ghost.recipesapp.model.db.RecipeWithImages
import dev.ghost.recipesapp.data.remote.LoadingState
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecipesViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    private val loadingState = MutableLiveData<LoadingState>()
    lateinit var data: MutableLiveData<List<RecipeWithImages>>

    private val searchingData = MutableLiveData("%%")
    private val sortingData = MutableLiveData("name")

    lateinit var recipesAdapter: RecipesAdapter

    private val filtersMediator = MediatorLiveData<FilteringParams>()

    init {
        (application as RecipesApplication).appComponent.inject(this)
        observeMediator()
        fetchData()
    }

    fun getLoadingState() = loadingState
    fun getSortingData() = sortingData
    fun getSearchingData() = searchingData

    private fun observeMediator() {
        filtersMediator.addSource(searchingData) {
            filtersMediator.postValue(
                FilteringParams(
                    it,
                    sortingData.value.toString()
                )
            )
        }
        filtersMediator.addSource(sortingData) {
            filtersMediator.postValue(
                FilteringParams(
                    searchingData.value.toString(),
                    it
                )
            )
        }
    }

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

    val results: LiveData<List<RecipeWithImages>> =
        Transformations.switchMap(filtersMediator) {
            recipesRepository.getRecipesByFilters(it.searching)
        }
}