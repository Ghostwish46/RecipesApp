package dev.ghost.recipesapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext() = app as Context
}