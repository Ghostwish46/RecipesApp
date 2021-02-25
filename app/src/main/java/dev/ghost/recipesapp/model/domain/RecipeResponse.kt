package dev.ghost.recipesapp.model.domain

import com.google.gson.annotations.SerializedName

class RecipeResponse(
    @SerializedName("recipes") val recipes: List<RecipeResult>
) {
}