package dev.ghost.recipesapp.presentation.recipe_details

import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ItemSimilarRecipeBinding
import dev.ghost.recipesapp.model.db.RecipeWithImages
import dev.ghost.recipesapp.presentation.utils.PlaceholderShimmerDrawable

class SimilarRecipeViewHolder(private val itemSimilarRecipeBinding: ItemSimilarRecipeBinding) :
    RecyclerView.ViewHolder(itemSimilarRecipeBinding.root) {

    fun bind(
        recipeWithImages: RecipeWithImages,
        onCardClickListener: (recipeWithImages: RecipeWithImages) -> Unit
    ) {
        with(itemSimilarRecipeBinding) {
            similarRecipeName.text = recipeWithImages.recipe.name

            recipeCard.setOnClickListener {
                onCardClickListener(recipeWithImages)
            }

            if (recipeWithImages.images.isNotEmpty())
                Glide.with(similarRecipeImage)
                    .load(recipeWithImages.images.first().path)
                    .placeholder(PlaceholderShimmerDrawable(root.context).shimmerDrawable)
                    .error(R.drawable.ic_error_loading)
                    .into(similarRecipeImage)
            else
                similarRecipeImage.setImageDrawable(getDrawable(root.context, R.drawable.ic_recipe_book))
        }
    }
}