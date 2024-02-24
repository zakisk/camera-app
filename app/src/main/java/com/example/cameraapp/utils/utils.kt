package com.example.cameraapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.toFile(context: Context): File? {
    var fos: FileOutputStream? = null
    val file: File?
    return try {
        val fileName = "IMG_${System.currentTimeMillis()}.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        file = File.createTempFile(fileName, ".jpg", storageDir)
        fos = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        file
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } finally {
        fos?.close()
    }
}

fun Bitmap.rotateBitmap(rotationDegrees: Int): Bitmap {
    val matrix = Matrix().apply {
        postRotate(-rotationDegrees.toFloat())
        postScale(-1f, -1f)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun File.toUri(context: Context): Uri {
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", this)
}

fun Uri.toFile(context: Context): File? {
    return this.path?.let { File(it) }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}