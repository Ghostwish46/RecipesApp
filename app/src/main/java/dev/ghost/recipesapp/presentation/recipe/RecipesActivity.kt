package dev.ghost.recipesapp.presentation.recipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipesBinding
import dev.ghost.recipesapp.model.network.Status
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

        activityRecipesBinding.recyclerRecipes.apply {
            adapter = recipesViewModel.recipesAdapter
            layoutManager = LinearLayoutManager(this@RecipesActivity)
        }

        activityRecipesBinding.refreshRecipes.setOnRefreshListener {
            recipesViewModel.fetchData()
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
                    activityRecipesBinding.layoutLoading.mainContainer.isVisible = false
                    activityRecipesBinding.refreshRecipes.isRefreshing = false
                }
                Status.FAILED -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_occurred) + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    activityRecipesBinding.layoutLoading.mainContainer.isVisible = false
                    activityRecipesBinding.refreshRecipes.isRefreshing = false
                }
            }
        })
    }

    private fun observeData() {
        recipesViewModel.getData().observe(this, Observer {
            if (it.isNotEmpty()) {
                recipesViewModel.recipesAdapter.submitList(it)
            }
        })
    }
}