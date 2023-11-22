package com.example.hobbyapp.HomeActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hobbyapp.R
import androidx.activity.viewModels
import com.example.hobbyapp.CreateUserActivity.CreateUserActivity
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.Util.UserApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private lateinit var user:User
    private lateinit var welcomeMsg: TextView
    private lateinit var settingsBtn: FloatingActionButton
    private var id:Int = -1
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.HomeViewModelFactory((application as UserApplication).repository)
    }
    //random comment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)
        //initializes variables for User
        welcomeMsg = findViewById(R.id.welcomeMsg)
        settingsBtn = findViewById(R.id.settingsBtn)

        //retrieves user id from LoginActivity
        id = intent.getIntExtra("USER_ID",-1)

        //Load User Information
        loadUser(id)
        settingsBtn.setOnClickListener {
            Log.d("HomeActivity", "Going to SettingsActivity")
        }
    }

    private fun loadUser(id:Int){
        homeViewModel.start(id)
        homeViewModel.user.observe(this) { it ->
            if (it != null) {
                user = it
                welcomeMsg.setText("Welcome back, ${user.fname} ${user.lname}")
                //set everything else here
            }
        }
    }
}