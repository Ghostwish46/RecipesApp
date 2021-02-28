package dev.ghost.recipesapp.presentation.recipe_images

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.ghost.recipesapp.App
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import javax.inject.Inject

class RecipeImagesViewModel(application: Application) :AndroidViewModel(application) {
    @Inject
    lateinit var recipesRepository: RecipesRepository

    lateinit var recipeImagesAdapter: RecipeImagesAdapter

    init {
        (application as App).appComponent.inject(this)
    }

    // Нужно ли сделать отдельный метод для RecipeWithImages, не таская похожих?
    fun getImages(uuid:String) = recipesRepository.getRecipeById(uuid)
}