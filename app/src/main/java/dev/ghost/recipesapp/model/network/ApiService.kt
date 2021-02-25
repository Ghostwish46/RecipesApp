package dev.ghost.recipesapp.model.network


import dev.ghost.recipesapp.model.domain.RecipeResponse
import dev.ghost.recipesapp.model.domain.RecipeResult
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @GET("recipes")
    fun getRecipesAsync(): Deferred<RecipeResponse>
}