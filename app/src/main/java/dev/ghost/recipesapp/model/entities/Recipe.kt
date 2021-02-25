package dev.ghost.recipesapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.ref.SoftReference
import java.util.*

@Entity(tableName = "recipes")
class Recipe(
    @PrimaryKey
    val uuid: String,
    val name: String,
) {
    var lastUpdated: Int = 0
    var description: String = ""
    var instructions: String = ""
    var difficulty: Int = 0
}