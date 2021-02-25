package dev.ghost.recipesapp.presentation.recipe

import androidx.recyclerview.widget.DiffUtil
import dev.ghost.recipesapp.model.entities.RecipeWithImages

object RecipesDiffCallback : DiffUtil.ItemCallback<RecipeWithImages>() {
    override fun areItemsTheSame(oldItem: RecipeWithImages, newItem: RecipeWithImages): Boolean {
        return oldItem.recipe.uuid == newItem.recipe.uuid
    }

    override fun areContentsTheSame(oldItem: RecipeWithImages, newItem: RecipeWithImages): Boolean {
        return oldItem == newItem
    }
}