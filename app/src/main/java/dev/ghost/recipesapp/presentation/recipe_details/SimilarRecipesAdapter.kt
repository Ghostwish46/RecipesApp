package dev.ghost.recipesapp.presentation.recipe_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.recipesapp.databinding.ItemSimilarRecipeBinding
import dev.ghost.recipesapp.model.db.RecipeWithImages
import dev.ghost.recipesapp.presentation.recipe.RecipesDiffCallback

class SimilarRecipesAdapter(
    private val onCardClickListener: (RecipeWithImages) -> Unit
) : RecyclerView.Adapter<SimilarRecipeViewHolder>() {

    private val differ = AsyncListDiffer(this, RecipesDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarRecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemSimilarRecipeBinding.inflate(layoutInflater, parent, false)
        return SimilarRecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarRecipeViewHolder, position: Int) {
        val currentRecipe = differ.currentList[position]
        holder.bind(currentRecipe, onCardClickListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(recipes: List<RecipeWithImages>) {
        differ.submitList(recipes)
    }
}