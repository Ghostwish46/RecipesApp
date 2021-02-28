package dev.ghost.recipesapp.model.db

import androidx.room.Entity

@Entity(tableName = "recipeSimilar", primaryKeys = ["originalUUID", "similar"])
class RecipeSimilar(
    val originalUUID: String,
    val similar: String
) {
}