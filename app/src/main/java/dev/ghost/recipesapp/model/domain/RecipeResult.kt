package dev.ghost.recipesapp.model.domain

import com.google.gson.annotations.SerializedName
import java.util.*

data class RecipeResult(
    @SerializedName("uuid") val uuid: UUID,
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("lastUpdated") val lastUpdated: Long,
    @SerializedName("description") val description: String?,
    @SerializedName("instructions") val instructions: String,
    @SerializedName("difficulty") val difficulty: Int
) {
}