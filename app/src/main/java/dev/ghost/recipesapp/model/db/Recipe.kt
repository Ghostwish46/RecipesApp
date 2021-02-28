package dev.ghost.recipesapp.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class Recipe(
    @PrimaryKey
    val uuid: String,
    val name: String,
) {
    var lastUpdated: Long = 0
    var description: String = ""
    var instructions: String = ""
    var difficulty: Int = 0
}