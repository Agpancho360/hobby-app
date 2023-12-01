package com.example.hobbyapp.LoginActivity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hobbyapp.CreateUserActivity.CreateUserActivity
import com.example.hobbyapp.HomeActivity.HomeActivity
import com.example.hobbyapp.R
import com.example.hobbyapp.UserDatabase.UserViewModel
import com.example.hobbyapp.Util.UserApplication

class LoginActivity : AppCompatActivity() {
    private lateinit var signupBtn:TextView
    private lateinit var loginBtn:TextView
    private lateinit var email:EditText
    private lateinit var password:EditText
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as UserApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        //initializes variables
        signupBtn = findViewById(R.id.input_signup)
        loginBtn = findViewById(R.id.input_submit)
        email = findViewById(R.id.input_email)
        password = findViewById(R.id.input_password)
        //onclick listener to create new user
        signupBtn.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }

        //onclick listener to login in
        loginBtn.setOnClickListener {
            //check if email exist in database
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            userViewModel.checkUserExistsByEmailAndPassword(emailText, passwordText)
        }

        //checks if user exists in data based on email/password
        userViewModel.loginResult.observe(this) { user ->
            // User successfully logged in, handle the success
            if (user != null) {
                Log.d("LoginActivity", "User logged in successfully: User ID --> ${user.id}")
                //Goes to HomeActivity if successful
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_ID", user.id) //sends user id to HomeActivity
                startActivity(intent)
            } else {
                // Login failed, handle the failure
                Log.d("LoginActivity", "Login failed: User not found")
            }
        }
    }
}