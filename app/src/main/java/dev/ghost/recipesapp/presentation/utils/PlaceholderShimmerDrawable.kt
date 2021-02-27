package dev.ghost.recipesapp.presentation.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dev.ghost.recipesapp.R

class PlaceholderShimmerDrawable(context: Context) {
    private val shimmerForLoading = Shimmer.ColorHighlightBuilder()
        .setBaseColor(ContextCompat.getColor(context, R.color.light_gray))
        .setBaseAlpha(1F)
        .setHighlightColor(ContextCompat.getColor(context, R.color.gray))
        .setHighlightAlpha(1F)
        .setDropoff(50F)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmerForLoading)
    }
}