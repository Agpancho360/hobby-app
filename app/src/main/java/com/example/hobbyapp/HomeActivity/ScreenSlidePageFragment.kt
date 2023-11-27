package com.example.hobbyapp.HomeActivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hobbyapp.R
import com.example.hobbyapp.UserDatabase.User
import java.io.File
import kotlin.math.roundToInt

class ScreenSlidePageFragment : Fragment() {

    private lateinit var user: User
    private var currentPhotoPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.user_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPicture: ImageView = view.findViewById(R.id.user_picture)
        val userName: TextView = view.findViewById(R.id.user_name)

        // Set user information to UI elements
        userName.text = "${user.fname} ${user.lname}"
        currentPhotoPath = user.image

        // Check if the file exists
        val file = File(currentPhotoPath)
        if (file.exists()) {
            val bitmap = getRoundedCornerBitmap(currentPhotoPath)
            userPicture.setImageBitmap(bitmap)
        } else {
            Log.d("ScreenSlidePageFragment", "File does not exist at path: $currentPhotoPath")
            userPicture.setImageDrawable(null)
        }
    }

    private fun getRoundedCornerBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        val targetSize: Int = minOf(options.outWidth, options.outHeight)
        val scaleFactor = Math.max(1, Math.min(options.outWidth / targetSize, options.outHeight / targetSize))

        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        val originalBitmap = BitmapFactory.decodeFile(filePath, options)

        val outputBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        val rect = Rect(0, 0, originalBitmap.width, originalBitmap.height)
        val roundPx = 20f  // Adjust the corner radius as needed

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(RectF(rect), roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(originalBitmap, rect, rect, paint)

        return outputBitmap
    }

    fun setUser(user: User) {
        this.user = user
    }
}

