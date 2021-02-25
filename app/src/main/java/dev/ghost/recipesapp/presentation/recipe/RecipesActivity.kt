package dev.ghost.recipesapp.presentation.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipesBinding
import dev.ghost.recipesapp.model.network.Status

class RecipesActivity : AppCompatActivity() {
    lateinit var activityRecipesBinding: ActivityRecipesBinding
    lateinit var recipesViewModel: RecipesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipesBinding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(activityRecipesBinding.root)

         recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        activityRecipesBinding.recyclerRecipes.apply {
            adapter = recipesViewModel.recipesAdapter
            layoutManager = GridLayoutManager(this@RecipesActivity, 1)
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
                }
                Status.FAILED -> {
                    Toast.makeText(
                        this,
                        getString(R.string.text_error) + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    activityRecipesBinding.layoutLoading.mainContainer.isVisible = false
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