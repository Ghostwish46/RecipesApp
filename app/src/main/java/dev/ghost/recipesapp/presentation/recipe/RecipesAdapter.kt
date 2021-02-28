package dev.ghost.recipesapp.presentation.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.recipesapp.databinding.ItemRecipeBinding
import dev.ghost.recipesapp.model.db.RecipeWithImages

class RecipesAdapter(
    private val onCardClickListener: (RecipeWithImages) -> Unit
) : RecyclerView.Adapter<RecipeViewHolder>() {

    private val differ = AsyncListDiffer(this, RecipesDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemRecipeBinding.inflate(layoutInflater, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
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