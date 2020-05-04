package com.app.assignment3.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.app.assignment3.R
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

fun hasInternet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}
// Method to save an image to internal storage
fun saveImageToInternalStorage(bitmap: Bitmap, context: Context): String {

    // Get the context wrapper instance
    val wrapper = ContextWrapper(context.applicationContext)

    // Initializing a new file
    // The bellow line return a directory in internal storage
    var file = wrapper.getDir("images", Context.MODE_PRIVATE)


    // Create a file to save the image
    file = File(file, "${UUID.randomUUID()}.jpg")

    try {
        // Get the file output stream
        val stream: OutputStream = FileOutputStream(file)

        // Compress bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        // Flush the stream
        stream.flush()

        // Close stream
        stream.close()
    } catch (e: IOException) { // Catch the exception
        e.printStackTrace()
    }

    // Return the saved image uri
    return file.absolutePath
}


fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"


fun ImageView.loadFlagByCode(context: Context, code: String) {
    val imageResource = context.resources
        .getIdentifier(
            "flag_" + code.toLowerCase(Locale.ENGLISH), "drawable",
            context.packageName
        )
    this.setImageResource(imageResource)
}

fun ImageView.setImageWithGlide(bitmap: Bitmap) {
    Glide.with(context)
        .load(bitmap)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.downloadImage(url: String?) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}
