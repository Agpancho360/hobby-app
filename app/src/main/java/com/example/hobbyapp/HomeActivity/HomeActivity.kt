package com.example.hobbyapp.HomeActivity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hobbyapp.R
import androidx.activity.viewModels
import com.example.hobbyapp.UserDatabase.User
import com.example.hobbyapp.Util.UserApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hobbyapp.UserDatabase.UserRepository
import com.example.hobbyapp.UserDatabase.UserViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var welcomeMsg: TextView
    private lateinit var settingsBtn: FloatingActionButton
    private var id: Int = -1
    private lateinit var viewPager: ViewPager2
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        // initializes variables for User
        welcomeMsg = findViewById(R.id.welcomeMsg)
        settingsBtn = findViewById(R.id.settingsBtn)

        // retrieves user id from LoginActivity
        id = intent.getIntExtra("USER_ID", -1)

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager)
        viewPager.setPageTransformer(ZoomOutPageTransformer())


        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // Load User Information
    //    loadUser(id)

        // Load Users
        loadUsers()

        settingsBtn.setOnClickListener {
            Log.d("HomeActivity", "Going to SettingsActivity")
            finish()
        }
    }



    private fun loadUsers() {
        userViewModel.allUsers.observe(this) { userList ->
            // Find the user with the specified ID
            val currentUser = userList.find { it.id == id }

            // Update welcome message
            currentUser?.let {
                welcomeMsg.text = "Welcome back ${it.fname} ${it.lname}"
            }

            // Filter out users with the specified user ID
            val filteredUsers = userList.filter { it.id != id }

            // Pass the updated list of users to the existing adapter
            (viewPager.adapter as ScreenSlidePagerAdapter).updateData(filteredUsers)
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private var userList: List<User> = emptyList()

        fun updateData(newList: List<User>) {
            userList = newList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = userList.size

        override fun createFragment(position: Int): Fragment {
            val fragment = ScreenSlidePageFragment()
            fragment.setUser(userList[position])
            return fragment
        }
    }
}
