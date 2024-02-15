package com.mohamed.tasks.core.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import java.math.BigInteger
import java.security.MessageDigest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object UtilHelper {

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun getDominantColor(bitmap: Bitmap): Int {
        // instead of using 3rd party lib like palette.
        val colorMap = HashMap<Int, Int>()

        // Iterate over each pixel in the bitmap
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)

                // Calculate the frequency of each color
                if (colorMap.containsKey(pixel)) {
                    colorMap[pixel] = colorMap[pixel]!! + 1
                } else {
                    colorMap[pixel] = 1
                }
            }
        }

        // Find the color with the highest frequency
        var dominantColor = 0
        var maxFrequency = 0
        for ((color, frequency) in colorMap) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency
                dominantColor = color
            }
        }

        return dominantColor
    }

     fun arePermissionsGranted(context: Context): Boolean {
        val permission:String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        }else{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        val   writePermission = ContextCompat.checkSelfPermission(
            context,
            permission)
        return writePermission == PackageManager.PERMISSION_GRANTED
    }

     fun requestPermission(requestPermissionLauncher: ActivityResultLauncher<Array<String>>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.POST_NOTIFICATIONS,
            )
            requestPermissionLauncher.launch(permissions)
        } else {
            val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
            requestPermissionLauncher.launch(permissions)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveImageToGalleryWithCaption(context: Context, imageUrl: String, caption: String?,fileName:String?) {
        val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imageDir, fileName.toString())
        if (!imageDir.exists()){
            imageDir.mkdirs()
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Download the image from the URL
                val inputStream = URL(imageUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Save the image to the file
                val fileOutputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

                // Add the caption to the image file metadata
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.TITLE, caption)
                    put(MediaStore.Images.Media.DESCRIPTION, caption)
                }
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Trigger MediaScanner to scan the saved image file
                MediaScannerConnection.scanFile(context, arrayOf(imageFile.absolutePath), null, null)
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.R)
    fun saveImageToGalleryWithCaptionAbove30(context: Context, imageUrl: String, caption: String?,fileName:String?) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName.toString())
            put(MediaStore.Images.Media.TITLE, caption)
            put(MediaStore.Images.Media.DESCRIPTION, caption)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Download the image from the URL
                val inputStream = URL(imageUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Save the image to the MediaStore
                val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (imageUri != null) {
                    val outputStream = resolver.openOutputStream(imageUri)
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                    outputStream?.flush()
                    outputStream?.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}