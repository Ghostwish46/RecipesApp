package dev.ghost.recipesapp.presentation.recipe_images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.recipesapp.databinding.ItemRecipeImageBinding
import dev.ghost.recipesapp.model.db.RecipeImage

class RecipeImagesAdapter(
    private val onImageClickListener: (RecipeImage) -> Unit
) : RecyclerView.Adapter<RecipeImageViewHolder>() {

    private val differ = AsyncListDiffer(this, RecipeImagesDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemRecipeImageBinding.inflate(layoutInflater, parent, false)
        return RecipeImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeImageViewHolder, position: Int) {
        val currentRecipe = differ.currentList[position]
        holder.bind(currentRecipe, onImageClickListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getCurrentItem(position: Int): RecipeImage {
        return differ.currentList[position]
    }

    fun submitList(images: List<RecipeImage>) {
        differ.submitList(images)
    }
}