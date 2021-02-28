package dev.ghost.recipesapp.presentation.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipesBinding
import dev.ghost.recipesapp.data.remote.LoadingState
import dev.ghost.recipesapp.data.remote.Status
import dev.ghost.recipesapp.presentation.recipe_details.RecipeDetailsActivity

class RecipesActivity : AppCompatActivity() {

    lateinit var activityRecipesBinding: ActivityRecipesBinding
    lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipesBinding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(activityRecipesBinding.root)

        recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        recipesViewModel.recipesAdapter = RecipesAdapter {
            val intentDetails = Intent(this, RecipeDetailsActivity::class.java)
            intentDetails.putExtra(RecipeDetailsActivity.RECIPE_UUID, it.recipe.uuid)
            startActivity(intentDetails)
        }

        with(activityRecipesBinding) {
            recyclerRecipes.apply {
                adapter = recipesViewModel.recipesAdapter
                layoutManager = LinearLayoutManager(this@RecipesActivity)
            }

            refreshRecipes.setOnRefreshListener {
                recipesViewModel.fetchData()
            }

            editRecipesSearching.addTextChangedListener {
                recipesViewModel.getSearchingData().postValue("%${it.toString().toLowerCase()}%")
            }

            recipesSearchingClear.setOnClickListener {
                editRecipesSearching.text.clear()
            }

            rbSortByName.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    recipesViewModel.getSortingData().postValue("name")
            }

            rbSortByLastUpdated.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    recipesViewModel.getSortingData().postValue("lastUpdated")
            }
        }

        observeData()
        observeLoadingStates()
    }

    private fun observeLoadingStates() {
        recipesViewModel.getLoadingState().observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    activityRecipesBinding.layoutLoading.mainContainer.isVisible = true
                }
                Status.SUCCESS -> {
                    finishLoading()
                }
                Status.FAILED -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_occurred) + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    finishLoading()
                }
            }
        })
    }

    private fun observeData() {
        recipesViewModel.resultsData.observe(this, { recipesList ->
            if (recipesList.isEmpty() && recipesViewModel.getLoadingState().value != LoadingState.LOADING)
                hideRecipesData()
            else if (recipesList.isNotEmpty()) {
                if (recipesViewModel.getSortingData().value == "name")
                    recipesViewModel.recipesAdapter.submitList(recipesList)
                else
                    recipesViewModel.recipesAdapter.submitList(recipesList.sortedByDescending { recipeData ->
                        recipeData.recipe.lastUpdated
                    })
                showRecipesData()
            }
        })
    }

    private fun finishLoading() {
        activityRecipesBinding.layoutLoading.mainContainer.isVisible = false
        activityRecipesBinding.refreshRecipes.isRefreshing = false
    }

    private fun showRecipesData() {
        activityRecipesBinding.recyclerRecipes.isVisible = true
        activityRecipesBinding.layoutNothing.mainContainer.isVisible = false
    }

    private fun hideRecipesData() {
        activityRecipesBinding.recyclerRecipes.isVisible = false
        activityRecipesBinding.layoutNothing.mainContainer.isVisible = true
    }
}