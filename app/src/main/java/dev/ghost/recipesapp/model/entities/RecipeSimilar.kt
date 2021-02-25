package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipeSimilar")
class RecipeSimilar(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val originalUUID: UUID,
    val similar: UUID
) {
}