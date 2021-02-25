package dev.ghost.recipesapp.presentation.recipe

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ItemRecipeBinding
import dev.ghost.recipesapp.model.entities.Recipe
import dev.ghost.recipesapp.model.entities.RecipeWithImages

class RecipeViewHolder(private val itemRecipeBinding: ItemRecipeBinding) :
    RecyclerView.ViewHolder(itemRecipeBinding.root) {

    fun bind(recipeWithImages: RecipeWithImages) {
        with(itemRecipeBinding) {
            recipeName.text = recipeWithImages.recipe.name
            recipeDescription.text = recipeWithImages.recipe.description

            val shimmerForLoading = Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(root.context, R.color.light_gray))
                .setBaseAlpha(1F)
                .setHighlightColor(ContextCompat.getColor(root.context, R.color.gray))
                .setHighlightAlpha(1F)
                .setDropoff(50F)
                .build()
            val shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmerForLoading)
            }

            Glide.with(recipeImage)
                .load(recipeWithImages.images.first().path)
                .placeholder(shimmerDrawable)
                .error(R.drawable.ic_error_loading)
                .into(recipeImage)
        }
    }
}