package com.example.hobbyapp.CreateUserActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.hobbyapp.LoginActivity.LoginActivity
import com.example.hobbyapp.R
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.UserDatabase.UserViewModel
import com.example.hobbyapp.Util.UserApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HobbiesActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var swimming: ToggleButton
    private lateinit var drawing: ToggleButton
    private lateinit var gardening: ToggleButton
    private lateinit var coding: ToggleButton
    private lateinit var traveling: ToggleButton
    private lateinit var photography: ToggleButton
    private lateinit var painting: ToggleButton
    private lateinit var music: ToggleButton
    private lateinit var writing: ToggleButton
    private lateinit var running: ToggleButton
    private lateinit var cooking: ToggleButton
    private var id: Int = -1
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_layout)
        swimming = findViewById(R.id.swimming)
        drawing = findViewById(R.id.drawing)
        gardening = findViewById(R.id.gardening)
        coding = findViewById(R.id.coding)
        traveling = findViewById(R.id.traveling)
        photography = findViewById(R.id.photography)
        painting = findViewById(R.id.painting)
        music = findViewById(R.id.music)
        writing = findViewById(R.id.writing)
        running = findViewById(R.id.running)
        cooking = findViewById(R.id.cooking)

        // retrieves data from createUserActivity
        val fname = intent.getStringExtra("first_name").toString()
        val lname = intent.getStringExtra("last_name").toString()
        val email = intent.getStringExtra("email").toString()
        val password = intent.getStringExtra("password").toString()
        val currentPhotoPath = intent.getStringExtra("currentPhotoPath").toString()

        if (id == -1) {
            user = User(
                id = null,
                fname = "",
                lname = "",
                email = "",
                password = "",
                image = "",
                swimming = swimming.isChecked,
                drawing = drawing.isChecked,
                gardening = gardening.isChecked,
                coding = coding.isChecked,
                traveling = traveling.isChecked,
                photography = photography.isChecked,
                painting = painting.isChecked,
                music = music.isChecked,
                writing = writing.isChecked,
                running = running.isChecked,
                cooking = cooking.isChecked
            )
        }

        // OnClickListener for when "Save" button is clicked
        findViewById<Button>(R.id.button).setOnClickListener {
            Log.d("PhotoActivity", "Save Clicked")
            user.fname = fname
            user.lname = lname
            user.email = email
            user.password = password
            user.image = currentPhotoPath
            user.swimming = swimming.isChecked
            user.drawing = drawing.isChecked
            user.gardening = gardening.isChecked
            user.coding = coding.isChecked
            user.traveling = traveling.isChecked
            user.photography = photography.isChecked
            user.painting = painting.isChecked
            user.music = music.isChecked
            user.writing = writing.isChecked
            user.running = running.isChecked
            user.cooking = cooking.isChecked

            // Insert new User object
            if (user.id == null) {
                userViewModel.insert(user)
                Log.d("HobbiesActivity", "User Object is $user")
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Finish the current activity
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (id != -1) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    Thread.sleep(200)
                    withContext(Dispatchers.Main) {

                    }
                }
            }
        }
    }

}
