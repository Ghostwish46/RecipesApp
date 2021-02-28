package dev.ghost.recipesapp.presentation.recipe_images

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dev.ghost.recipesapp.R
import dev.ghost.recipesapp.databinding.ActivityRecipeImagesBinding
import dev.ghost.recipesapp.model.db.RecipeImage
import dev.ghost.recipesapp.presentation.recipe_details.RecipeDetailsActivity
import dev.ghost.recipesapp.presentation.utils.FilesLoader
import java.lang.Exception
import java.util.*

class RecipeImagesActivity : AppCompatActivity() {
    companion object {
        const val DOWNLOADING_CODE = 1001
        const val ADAPTER_POSITION = "adapter_position"
    }

    lateinit var recipeImagesViewModel: RecipeImagesViewModel
    lateinit var activityRecipeImagesBinding: ActivityRecipeImagesBinding
    private var adapterPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipeImagesBinding = ActivityRecipeImagesBinding.inflate(layoutInflater)
        setContentView(activityRecipeImagesBinding.root)

        adapterPosition = intent.getIntExtra(ADAPTER_POSITION, 0)

        recipeImagesViewModel = ViewModelProvider(this).get(RecipeImagesViewModel::class.java)
        recipeImagesViewModel.recipeImagesAdapter = RecipeImagesAdapter {}

        with(activityRecipeImagesBinding) {
            setSupportActionBar(toolbar)

            recipeImagesViewPager.adapter =
                recipeImagesViewModel.recipeImagesAdapter

            recipeImageDownload.setOnClickListener {
                downloadImage()
            }

            recipeImagesViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    toolbar.title =
                        "${position + 1} of ${recipeImagesViewModel.recipeImagesAdapter.itemCount}"
                }
            })
        }

        val currentUUID = intent.getStringExtra(RecipeDetailsActivity.RECIPE_UUID)

        currentUUID?.let {
            observeRecipeImages(it)
        }
    }

    private fun observeRecipeImages(uuid: String) {
        recipeImagesViewModel.getImages(uuid).observe(this, {
            if (it.images.isNotEmpty()) {
                recipeImagesViewModel.recipeImagesAdapter.submitList(it.images)
                activityRecipeImagesBinding.recipeImagesViewPager.setCurrentItem(
                    adapterPosition,
                    false
                )
            }
        })
    }

    private fun downloadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            prepareImageLoading()
        } else {
            ActivityCompat
                .requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    DOWNLOADING_CODE
                )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == DOWNLOADING_CODE)
                prepareImageLoading()
        } else {
            showMessage(getString(R.string.error_access_denied))
        }
    }

    private fun prepareImageLoading() {
        try {
            val currentItem =
                recipeImagesViewModel.recipeImagesAdapter.getCurrentItem(
                    activityRecipeImagesBinding.recipeImagesViewPager.currentItem
                )
            startLoading(currentItem)
        } catch (ex: Exception) {
            showMessage(ex.message.toString())
        }
    }

    private fun startLoading(currentItem: RecipeImage) {
        Glide.with(this)
            .asBitmap()
            .load(currentItem.path)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    try {
                        val resultMessage =
                            FilesLoader().saveImageToGallery(resource, this@RecipeImagesActivity)
                        showMessage(resultMessage)
                    } catch (ex: Exception) {
                        showMessage(ex.message.toString())
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}