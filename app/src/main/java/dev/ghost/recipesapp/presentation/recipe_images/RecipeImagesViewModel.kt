package dev.ghost.recipesapp.presentation.recipe_images

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.ghost.recipesapp.RecipesApplication
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import javax.inject.Inject

class RecipeImagesViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    lateinit var recipeImagesAdapter: RecipeImagesAdapter

    init {
        (application as RecipesApplication).appComponent.inject(this)
    }

    fun getImages(uuid: String) = recipesRepository.getRecipeWithImagesByUUID(uuid)
}