package dev.ghost.recipesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.ghost.recipesapp.model.db.RecipesDataBase
import javax.inject.Singleton

@Module
class DataBaseModule {
    private val dbName = "RecipesDatabase"

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            RecipesDataBase::class.java,
            dbName
    )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideRecipesDao(recipesDataBase: RecipesDataBase) = recipesDataBase.recipesDao()

    @Provides
    @Singleton
    fun provideRecipeImagesDao(recipesDataBase: RecipesDataBase) = recipesDataBase.recipeImagesDao()

    @Provides
    @Singleton
    fun provideRecipeSimilarDao(recipesDataBase: RecipesDataBase) = recipesDataBase.recipeSimilarDao()
}
