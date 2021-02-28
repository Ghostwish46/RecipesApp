package dev.ghost.recipesapp.data.remote


import dev.ghost.recipesapp.model.api.RecipeDetailsResponse
import dev.ghost.recipesapp.model.api.RecipeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @GET("recipes")
    fun getRecipesAsync(): Deferred<RecipeResponse>

    @GET("recipes/{uuid}")
    fun getRecipeDetailsAsync(@Path("uuid") uuid: String): Deferred<RecipeDetailsResponse>
}