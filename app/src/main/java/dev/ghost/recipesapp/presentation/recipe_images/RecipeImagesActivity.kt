package dev.ghost.recipesapp.presentation.recipe_images

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class RecipeImagesActivity : AppCompatActivity() {
    companion object {
        const val DOWNLOADING_CODE = 1001
    }

    lateinit var recipeImagesViewModel: RecipeImagesViewModel
    lateinit var activityRecipeImagesBinding: ActivityRecipeImagesBinding

    private val formatter =
        SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.UK)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecipeImagesBinding = ActivityRecipeImagesBinding.inflate(layoutInflater)
        setContentView(activityRecipeImagesBinding.root)

        setSupportActionBar(activityRecipeImagesBinding.toolbar)

        recipeImagesViewModel = ViewModelProvider(this).get(RecipeImagesViewModel::class.java)

        recipeImagesViewModel.recipeImagesAdapter = RecipeImagesAdapter {}

        activityRecipeImagesBinding.recipeImagesViewPager.adapter =
            recipeImagesViewModel.recipeImagesAdapter

        val currentUUID = intent.getStringExtra(RecipeDetailsActivity.RECIPE_UUID)

        currentUUID?.let {
            observeRecipeImages(it)
        }

        activityRecipeImagesBinding.recipeImageDownload.setOnClickListener {
            downloadImage()
        }

        activityRecipeImagesBinding.recipeImagesViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                activityRecipeImagesBinding.toolbar.title =
                    "${position + 1} of ${recipeImagesViewModel.recipeImagesAdapter.itemCount}"
            }
        })
    }

    private fun observeRecipeImages(uuid: String) {
        recipeImagesViewModel.getImages(uuid).observe(this, {
            if (it.images.isNotEmpty())
                recipeImagesViewModel.recipeImagesAdapter.submitList(it.images)
        })
    }

    private fun downloadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val currentItem =
                    recipeImagesViewModel.recipeImagesAdapter.getCurrentItem(
                        activityRecipeImagesBinding.recipeImagesViewPager.currentItem
                    )
                initLoading(currentItem)
            } catch (ex: Exception) {
                showMessage(ex.message.toString())
            }
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
            try {
                val currentItem =
                    recipeImagesViewModel.recipeImagesAdapter.getCurrentItem(
                        activityRecipeImagesBinding.recipeImagesViewPager.currentItem
                    )
                if (requestCode == DOWNLOADING_CODE) {
                    initLoading(currentItem)
                }
            } catch (ex: Exception) {
                showMessage(ex.message.toString())
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        } else {
            showMessage(getString(R.string.error_access_denied))
        }
    }

    private fun initLoading(currentItem: RecipeImage) {
        Glide.with(this)
            .asBitmap()
            .load(currentItem.path)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    try {
                        saveImageToGallery(resource)
                    } catch (ex: Exception) {
                        showMessage(ex.message.toString())
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun saveImageToGallery(resource: Bitmap) {
        val now = Date()
        val fileName: String = formatter.format(now) + ".png"

        val file =
            File(
                Environment.getExternalStorageDirectory().toString() + "/Download/",
                fileName
            )
        saveFile(resource, file)
        this.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.absolutePath)
            )
        )
        showMessage(getString(R.string.info_file_saved) + file.toString())
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun saveFile(resource: Bitmap, file: File) {
        val stream = FileOutputStream(file)
        resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
    }

}