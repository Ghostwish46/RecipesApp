package dev.ghost.recipesapp.presentation.recipe

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ItemRecipeBinding
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.presentation.utils.PlaceholderShimmerDrawable

class RecipeViewHolder(private val itemRecipeBinding: ItemRecipeBinding) :
    RecyclerView.ViewHolder(itemRecipeBinding.root) {

    fun bind(
        recipeWithImages: RecipeWithImages,
        onCardClickListener: (recipeWithImages: RecipeWithImages) -> Unit
    ) {
        with(itemRecipeBinding) {
            recipeName.text = recipeWithImages.recipe.name
            recipeDescription.text =
                if (recipeWithImages.recipe.description.isBlank())
                    root.context.getString(R.string.placeholder_description)
                else recipeWithImages.recipe.description

            recipeCard.setOnClickListener {
                onCardClickListener(recipeWithImages)
            }

            if (recipeWithImages.images.isNotEmpty())
                Glide.with(recipeImage)
                    .load(recipeWithImages.images.first().path)
                    .placeholder(PlaceholderShimmerDrawable(root.context).shimmerDrawable)
                    .error(R.drawable.ic_error_loading)
                    .into(recipeImage)
            else
                recipeImage.setImageDrawable(getDrawable(root.context, R.drawable.ic_recipe_book))
        }
    }
}