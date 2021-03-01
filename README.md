# Recipes App
<b>Recipes App is the most necessary app for you and your family. With this application you can always find the right recipe for a light breakfast, a healthy lunch or a hearty dinner.</b>
## Interface
![First image](https://github.com/Ghostwish46/ProjectImages/blob/main/Untitled.png)
## Project setup
Clone the repository, open the project in Android Studio, click "Run".

## Architecture
Based on mvvm architecture and repository pattern. <br/>
![Architecture](https://github.com/Ghostwish46/ProjectImages/blob/main/96736485-54601480-13c5-11eb-8bd1-2308f224d58b.png)

## Tech Stack
* Minimum SDK 21
* MVVM Architecture
* ViewBinding
* Written on kotlin language
* Architecture Components (Lifecycle, LiveData, ViewModel, Room Persistence)
* [API](https://test.kode-t.ru/recipes) with recipes data
* [Material Design](https://material.io/develop/android/docs/getting-started) for implementing material design
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for threading operations
* [Swipe Refresh Layout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) for swipe to sync funtion
* [Retrofit 2](https://github.com/square/retrofit) for constructing the REST API
* [Gson](https://github.com/google/gson) for parsing JSON
* [OkHttp3](https://github.com/square/okhttp) for logging
* [Dagger 2](https://github.com/google/dagger) for dependency injection
* [Glide](https://github.com/bumptech/glide) for image loading
* [Shimmer](https://github.com/facebook/shimmer-android) for loading placeholders

## Notes about project
В техническом задании не оговаривалось хранение похожих рецептов в базе данных, 
поэтому я решил хранить их наравне с рецептами из общего списка с API, подгружая информацию о них и выводя на главном экране.

## Contacts
For any questions, you can contact me in Telegram (<b>@Ghostwish<b>).
