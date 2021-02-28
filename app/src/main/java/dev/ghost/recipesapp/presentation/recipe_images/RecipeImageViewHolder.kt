package dev.ghost.recipesapp.presentation.recipe_images

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ItemRecipeImageBinding
import dev.ghost.recipesapp.model.db.RecipeImage
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