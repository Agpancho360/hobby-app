package com.example.hobbyapp.CreateUserActivity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.hobbyapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateUserActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private var currentPhotoPath: String = ""
    private val takePictureResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_CANCELED){
            Log.d("CreateUserActivity","Take Picture Activity Cancelled")
        }else{
            Log.d("CreateUserActivity", "Picture Taken")
            setPic()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user_layout)
        imageView = findViewById(R.id.picture)
        fname = findViewById(R.id.first_name)
        lname = findViewById(R.id.last_name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        //On clickListener for when "Save" button is clicked
        findViewById<Button>(R.id.button).setOnClickListener{
            Log.d("PhotoActivity","Next Clicked")
            val intent = Intent(this, HobbiesActivity::class.java)
            intent.putExtra("first_name", fname.text.toString())
            intent.putExtra("last_name", lname.text.toString())
            intent.putExtra("email", email.text.toString())
            intent.putExtra("password", password.text.toString())
            intent.putExtra("currentPhotoPath", currentPhotoPath)
            startActivity(intent)

        }
        imageView.setOnClickListener{
            takeAPicture()
        }
    }
    //Following functions deal with take Photo and setting it
    private fun setPic() {
        val targetSize: Int = minOf(imageView.width, imageView.height)

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine the target dimensions to make the image square
        val targetW: Int
        val targetH: Int
        if (photoW > photoH) {
            targetW = targetSize
            targetH = targetSize
        } else {
            targetH = targetSize
            targetW = targetSize
        }

        // Determine how much to scale down the image
        val scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        // Decode the bitmap
        val originalBitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)

        // Create a circular bitmap
        val circularBitmap = getCircularBitmap(originalBitmap)

        // Set the circular bitmap to the ImageView
        imageView.setImageBitmap(circularBitmap)
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, bitmap.width / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return outputBitmap
    }



    private fun takeAPicture() {
        val picIntent: Intent = Intent().setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        if (picIntent.resolveActivity(packageManager) != null) {
            val filepath: String = createFilePath()
            val myFile: File = File(filepath)
            currentPhotoPath = filepath
            val photoUri =
                FileProvider.getUriForFile(this, "com.example.hobbyapp.fileprovider", myFile)
            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            takePictureResultLauncher.launch(picIntent)
        }
    }
    private fun createFilePath(): String {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intent
        return image.absolutePath
    }
}