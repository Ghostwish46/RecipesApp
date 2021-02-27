package dev.ghost.recipesapp.model.domain

import com.google.gson.annotations.SerializedName
import java.util.*

data class SimilarResult(
    @SerializedName("uuid") val uuid: UUID,
    @SerializedName("name") val name: String
) {
}