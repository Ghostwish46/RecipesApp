package dev.ghost.recipesapp.model.network


import dev.ghost.recipesapp.model.domain.RecipeDetailsResponse
import dev.ghost.recipesapp.model.domain.RecipeResponse
import dev.ghost.recipesapp.model.domain.RecipeResult
import kotlinx.coroutines.Deferred
import retrofit2.http.*
import java.util.*

interface ApiService {
    @GET("recipes")
    fun getRecipesAsync(): Deferred<RecipeResponse>

    @GET("recipes/{uuid}")
    fun getRecipeDetailsAsync(@Path("uuid") uuid: String): Deferred<RecipeDetailsResponse>
}