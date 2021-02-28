package dev.ghost.recipesapp.presentation.recipe_images

import androidx.recyclerview.widget.DiffUtil
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeWithImages

object RecipeImagesDiffCallback : DiffUtil.ItemCallback<RecipeImage>() {
    override fun areItemsTheSame(oldItem: RecipeImage, newItem: RecipeImage): Boolean {
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItem: RecipeImage, newItem: RecipeImage): Boolean {
        return oldItem == newItem
    }
}