package dev.ghost.recipesapp.di

import dagger.Module
import dagger.Provides
import dev.ghost.recipesapp.data.local.db.dao.RecipeImagesDao
import dev.ghost.recipesapp.data.local.db.dao.RecipeSimilarDao
import dev.ghost.recipesapp.data.local.db.dao.RecipesDao
import dev.ghost.recipesapp.data.remote.ApiService
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
