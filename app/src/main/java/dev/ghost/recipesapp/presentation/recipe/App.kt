package dev.ghost.recipesapp.presentation.recipe

import android.app.Application
import dev.ghost.recipesapp.di.AppComponent
import dev.ghost.recipesapp.di.AppModule
import dev.ghost.recipesapp.di.DaggerAppComponent

class App:Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }
}