package dev.ghost.recipesapp.presentation.recipe_details

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipeDetailsBinding
import dev.ghost.recipesapp.data.remote.LoadingState
import dev.ghost.recipesapp.data.remote.Status
import dev.ghost.recipesapp.presentation.recipe_images.RecipeImagesActivity
import dev.ghost.recipesapp.presentation.recipe_images.RecipeImagesAdapter
import dev.ghost.recipesapp.presentation.utils.PlaceholderShimmerDrawable

class RecipeDetailsActivity : AppCompatActivity() {
    companion object {
        const val RECIPE_UUID = "recipe_uuid"
    }

    lateinit var activityRecipeDetailsBinding: ActivityRecipeDetailsBinding
    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipeDetailsBinding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(activityRecipeDetailsBinding.root)

        recipeDetailsViewModel = ViewModelProvider(this).get(RecipeDetailsViewModel::class.java)

        activityRecipeDetailsBinding.layoutNoSimilarRecipes.nothingDescription.text =
            getString(R.string.error_no_similar_recipes)

        recipeDetailsViewModel.similarRecipesAdapter = SimilarRecipesAdapter {
            val intentDetails = Intent(this, RecipeDetailsActivity::class.java)
            intentDetails.putExtra(RECIPE_UUID, it.recipe.uuid)
            startActivity(intentDetails)
        }
        recipeDetailsViewModel.recipeImagesAdapter = RecipeImagesAdapter {
            val intentDetails = Intent(this, RecipeImagesActivity::class.java)
            intentDetails.putExtra(RECIPE_UUID, it.recipeUUID)
            intentDetails.putExtra(
                RecipeImagesActivity.ADAPTER_POSITION,
                activityRecipeDetailsBinding.recipeDetailsViewPagerImages.currentItem
            )
            startActivity(intentDetails)
        }

        val currentUUID = intent.getStringExtra(RECIPE_UUID)

        observeLoadingStates()
        currentUUID?.let {
            observeRecipeDetails(it)
        }

        with(activityRecipeDetailsBinding) {
            recipeDetailsRecyclerSimilar.apply {
                adapter = recipeDetailsViewModel.similarRecipesAdapter
                layoutManager =
                    LinearLayoutManager(this@RecipeDetailsActivity, RecyclerView.HORIZONTAL, false)
            }

            recipeDetailsViewPagerImages.registerOnPageChangeCallback(
                object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        textRecipeImagesPosition.text =
                            "${position + 1} of ${recipeDetailsViewModel.recipeImagesAdapter.itemCount}"
                    }
                })

            recipeDetailsViewPagerImages.adapter =
                recipeDetailsViewModel.recipeImagesAdapter
        }
    }

    private fun observeLoadingStates() {
        recipeDetailsViewModel.getLoadingState().observe(this, {
            when (it.status) {
                Status.RUNNING -> {
                    activityRecipeDetailsBinding.layoutSimilarRecipesLoading.mainContainer.isVisible =
                        true
                    activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = false
                }
                Status.SUCCESS -> {
                    activityRecipeDetailsBinding.layoutSimilarRecipesLoading.mainContainer.isVisible =
                        false
                    activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = true
                }
                Status.FAILED -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_occurred) + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    activityRecipeDetailsBinding.layoutSimilarRecipesLoading.mainContainer.isVisible =
                        false
                    activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = true
                }
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeRecipeDetails(uuid: String) {
        recipeDetailsViewModel.getRecipeWithSimilar(uuid)
            .observe(this, { recipeWithData ->
                with(activityRecipeDetailsBinding) {
                    recipeDetailsName.text = recipeWithData.recipe.name

                    recipeDetailsDescription.text =
                        if (recipeWithData.recipe.description.isBlank())
                            root.context.getString(R.string.placeholder_description)
                        else recipeWithData.recipe.description

                    recipeDetailsInstructions.text =
                        Html.fromHtml(recipeWithData.recipe.instructions)

                    recipeDetailsDifficulty.rating = recipeWithData.recipe.difficulty.toFloat()

                    if (recipeWithData.images.isNotEmpty()) {
                        recipeDetailsViewModel.recipeImagesAdapter.submitList(recipeWithData.images)
                        activityRecipeDetailsBinding.textRecipeImagesPosition.text =
                            "${activityRecipeDetailsBinding.recipeDetailsViewPagerImages.currentItem + 1} " +
                                    "of ${recipeDetailsViewModel.recipeImagesAdapter.itemCount}"
                    }

                    if (recipeWithData.similarRecipes.isEmpty() &&
                        recipeDetailsViewModel.getLoadingState().value != LoadingState.LOADING
                    )
                        hideSimilarRecipesData()
                    else if (recipeWithData.similarRecipes.isNotEmpty()) {
                        recipeDetailsViewModel.similarRecipesAdapter.submitList(recipeWithData.similarRecipes)
                        showSimilarRecipesData()
                        recipeDetailsViewModel.fetchSimilarRecipesInfo(recipeWithData.similarRecipes.filter {
                            it.images.isEmpty()
                        })
                    }
                }
            })
    }

    private fun showSimilarRecipesData() {
        if (recipeDetailsViewModel.getLoadingState().value != LoadingState.LOADING)
            activityRecipeDetailsBinding.layoutNoSimilarRecipes.mainContainer.isVisible = false
        activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = true
    }

    private fun hideSimilarRecipesData() {
        activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = false
        activityRecipeDetailsBinding.layoutNoSimilarRecipes.mainContainer.isVisible = true
    }
}