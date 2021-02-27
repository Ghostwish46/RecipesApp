package dev.ghost.recipesapp.di

import dagger.Module
import dagger.Provides
import dev.ghost.recipesapp.model.db.RecipeImagesDao
import dev.ghost.recipesapp.model.db.RecipeSimilarDao
import dev.ghost.recipesapp.model.db.RecipesDao
import dev.ghost.recipesapp.model.network.ApiService
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideRecipesRepository(
        recipesDao: RecipesDao,
        recipeImagesDao: RecipeImagesDao,
        recipeSimilarDao: RecipeSimilarDao,
        apiService: ApiService
    ) = RecipesRepository(recipesDao, recipeImagesDao, recipeSimilarDao, apiService)

}
