package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipeSimilar", primaryKeys = ["originalUUID", "similar"])
class RecipeSimilar(
    val originalUUID: String,
    val similar: String
) {
}