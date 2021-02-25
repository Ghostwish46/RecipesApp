package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipes")
class Recipe(
    @PrimaryKey
    val uuid: UUID,
    val name: String,
) {
    val lastUpdated: Int = 0
    val description: String = ""
    val instructions: String = ""
    val difficulty: Int = 0
}