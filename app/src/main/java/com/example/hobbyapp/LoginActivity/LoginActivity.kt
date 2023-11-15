package com.example.hobbyapp.LoginActivity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hobbyapp.CreateUserActivity.CreateUserActivity
import com.example.hobbyapp.R
import com.example.hobbyapp.Util.UserApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginActivity : AppCompatActivity() {
    private lateinit var signupBtn:TextView
    private lateinit var loginBtn:TextView
    private lateinit var email:EditText
    private lateinit var password:EditText
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.LoginViewModelFactory((application as UserApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        signupBtn = findViewById(R.id.input_signup)
        loginBtn = findViewById(R.id.input_submit)
        email = findViewById(R.id.input_email)
        password = findViewById(R.id.input_password)
        signupBtn.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            //check if email exist in database
//            val emailText = email.text.toString()
//            val passwordText = password.text.toString()
//            val (userExists, userId) = loginViewModel.checkUserExistsByEmailAndPassword(emailText,passwordText)
//            if (userExists) {
//                // 'userId' contains the ID of the user
//                Log.d(
//                    "LoginActivity",
//                    "User exists (ID) -->  $userId"
//                )
//            } else {
//                // User with the specified email does not exist in the database
//                Log.d(
//                    "LoginActivity",
//                    "User does not exist"
//                )
//            }
              Log.d(
                    "LoginActivity",
                    "LoginAcivity:  User was logged in"
                )
            //Create new landing page intent HERE

        }


    }

}