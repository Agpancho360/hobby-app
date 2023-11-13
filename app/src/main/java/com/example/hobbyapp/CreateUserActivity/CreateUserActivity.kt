package com.example.hobbyapp.CreateUserActivity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hobbyapp.R
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.Util.UserApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateUserActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private var id:Int = -1
    private val createUserViewModel:CreateUserViewModel by viewModels {
        CreateUserViewModel.CreateUserViewModelFactory((application as UserApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user_layout)
        fname = findViewById(R.id.fname)
        lname = findViewById(R.id.lname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        if (id == -1)
        {
            user = User(null,"","","","")
        }
        //On clickListener for when "Save" button is clicked
        findViewById<Button>(R.id.button).setOnClickListener{
            Log.d("PhotoActivity","Save Clicked")
            user.fname = fname.text.toString()
            user.lname = lname.text.toString()
            user.email = email.text.toString()
            user.password = password.text.toString()
            //insert new Photo object
            if(user.id==null){
                createUserViewModel.insert(user)
                Log.d(
                    "CreateUserActivity",
                    "User Object is ${user}"
                )
            }
            setResult(RESULT_OK)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        if(id != -1){
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    Thread.sleep(200)
                    withContext(Dispatchers.Main){

                    }
                }
            }
        }
    }
}