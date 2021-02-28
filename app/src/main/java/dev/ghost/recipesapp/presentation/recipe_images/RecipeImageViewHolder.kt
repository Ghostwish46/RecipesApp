package dev.ghost.recipesapp.presentation.recipe_images

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ItemRecipeBinding
import dev.ghost.recipesapp.databinding.ItemRecipeImageBinding
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeImage
import dev.ghost.recipesapp.model.entities.RecipeWithImages
import dev.ghost.recipesapp.presentation.utils.PlaceholderShimmerDrawable

class RecipeImageViewHolder(private val itemRecipeImageBinding: ItemRecipeImageBinding) :
    RecyclerView.ViewHolder(itemRecipeImageBinding.root) {

    fun bind(
        recipeImage: RecipeImage,
        onImageClickListener: (RecipeImage) -> Unit
    ) {
        with(itemRecipeImageBinding) {

            recipeImageView.setOnClickListener {
                onImageClickListener(recipeImage)
            }

            Glide.with(recipeImageView)
                .load(recipeImage.path)
                .placeholder(PlaceholderShimmerDrawable(root.context).shimmerDrawable)
                .error(R.drawable.ic_error_loading)
                .into(recipeImageView)

        }
    }
}