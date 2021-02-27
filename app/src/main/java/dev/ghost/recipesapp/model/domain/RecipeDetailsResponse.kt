package dev.ghost.recipesapp.model.domain

import com.google.gson.annotations.SerializedName

class RecipeDetailsResponse(
    @SerializedName("recipe") val recipe: RecipeResult
) {
}