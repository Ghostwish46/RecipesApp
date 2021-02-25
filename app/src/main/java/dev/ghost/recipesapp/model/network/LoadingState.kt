package dev.ghost.recipesapp.model.network

// Data class for checking network state while loading data.
data class LoadingState constructor(
    val status: Status,
    val message: String? = null
) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }
}