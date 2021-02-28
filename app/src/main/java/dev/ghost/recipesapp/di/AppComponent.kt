package dev.ghost.recipesapp.di

import android.app.Application
import dagger.Component
import dev.ghost.recipesapp.presentation.recipe.RecipesViewModel
import dev.ghost.recipesapp.presentation.recipe_details.RecipeDetailsViewModel
import dev.ghost.recipesapp.presentation.recipe_images.RecipeImagesViewModel
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataBaseModule::class, NetworkModule::class, StorageModule::class])
@Singleton
interface AppComponent {
    // Application
    fun inject(app: Application)

    // Activities

    // Fragments

    // ViewModels
    fun inject(recipesViewModel: RecipesViewModel)
    fun inject(recipeDetailsViewModel: RecipeDetailsViewModel)
    fun inject(recipeImagesViewModel: RecipeImagesViewModel)
}