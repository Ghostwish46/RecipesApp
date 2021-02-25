package dev.ghost.recipesapp.di

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataBaseModule::class, NetworkModule::class, StorageModule::class])
@Singleton
interface AppComponent {
    // Application
    fun inject(app: Application)

    // Activities

    // Fragments

    // ViewModels
}