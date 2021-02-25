package dev.ghost.recipesapp.model.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import dev.ghost.recipesapp.model.db.RecipesDao
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(private val recipesDao: RecipesDao, private val apiService: ApiService) {

    var data: LiveData<Recipe> = recipesDao.getData()

    suspend fun refresh() {
        withContext(Dispatchers.IO)
        {
            val response = apiService.getRecipesAsync()
                    .await()
            recipesDao.insert(response)
        }
    }
}