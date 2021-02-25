package dev.ghost.recipesapp.di

import dagger.Module
import dagger.Provides
import dev.ghost.recipesapp.model.db.RecipesDao
import dev.ghost.recipesapp.model.network.ApiService
import dev.ghost.recipesapp.model.repositories.RecipesRepository
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideRecipesRepository(recipesDao: RecipesDao, apiService: ApiService) =
            RecipesRepository(recipesDao, apiService)

}
