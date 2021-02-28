package dev.ghost.recipesapp.presentation.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import dev.ghost.recipesapp.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FilesLoader {
    private val formatter =
        SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.UK)

    fun saveImageToGallery(resource: Bitmap, context: Context) : String {
        val now = Date()
        val fileName: String = formatter.format(now) + ".png"

        val file =
            File(
                Environment.getExternalStorageDirectory().toString() + "/Download/",
                fileName
            )
        saveFile(resource, file)
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.absolutePath)
            )
        )
        return context.getString(R.string.info_file_saved) + file.toString()
    }

    private fun saveFile(resource: Bitmap, file: File) {
        val stream = FileOutputStream(file)
        resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
    }
}