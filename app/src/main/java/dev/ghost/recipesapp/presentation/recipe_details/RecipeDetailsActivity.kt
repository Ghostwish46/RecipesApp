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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipeDetailsBinding
import dev.ghost.recipesapp.model.network.Status
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

        recipeDetailsViewModel.similarRecipesAdapter = SimilarRecipesAdapter {
            val intentDetails = Intent(this, RecipeDetailsActivity::class.java)
            intentDetails.putExtra(RECIPE_UUID, it.recipe.uuid)
            startActivity(intentDetails)
        }

        activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.apply {
            adapter = recipeDetailsViewModel.similarRecipesAdapter
            layoutManager =
                LinearLayoutManager(this@RecipeDetailsActivity, RecyclerView.HORIZONTAL, false)
        }

        val currentUUID = intent.getStringExtra(RECIPE_UUID)

        observeLoadingStates()
        currentUUID?.let {
            observeRecipeDetails(it)
        }
    }

    private fun observeLoadingStates() {
        recipeDetailsViewModel.getLoadingState().observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    activityRecipeDetailsBinding.layoutLoading.mainContainer.isVisible = true
                    activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = false
                }
                Status.SUCCESS -> {
                    activityRecipeDetailsBinding.layoutLoading.mainContainer.isVisible = false
                    activityRecipeDetailsBinding.recipeDetailsRecyclerSimilar.isVisible = true
                }
                Status.FAILED -> {
                    Toast.makeText(
                        this,
                        getString(R.string.text_error) + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    activityRecipeDetailsBinding.layoutLoading.mainContainer.isVisible = false
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
                    recipeDetailsDescription.text = recipeWithData.recipe.description
                    recipeDetailsInstructions.text =
                        Html.fromHtml(recipeWithData.recipe.instructions)
                    recipeDetailsDifficulty.text = recipeWithData.recipe.difficulty.toString()

                    if (recipeWithData.images.isNotEmpty())
                        Glide.with(recipeDetailsImage)
                            .load(recipeWithData.images.first().path)
                            .placeholder(PlaceholderShimmerDrawable(this@RecipeDetailsActivity).shimmerDrawable)
                            .error(R.drawable.ic_error_loading)
                            .into(recipeDetailsImage)
                    else
                        recipeDetailsImage.setImageDrawable(getDrawable(R.drawable.ic_recipe_book))

                    if (recipeWithData.similarRecipes.isNotEmpty())
                        recipeDetailsViewModel.similarRecipesAdapter.submitList(recipeWithData.similarRecipes)
                }
            })
    }
}