package dev.ghost.recipesapp.model.network


import dev.ghost.recipesapp.model.entities.Recipe
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("recipes")
    fun getRecipesAsync(): Deferred<List<Recipe>>
}