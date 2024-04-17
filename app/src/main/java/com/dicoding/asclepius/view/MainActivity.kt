package com.dicoding.asclepius.view

import android.os.Bundle
import android.widget.PopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupSmoothBottomMenu()
        supportActionBar?.hide()
//        setupActionBarWithNavController(navController)
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.menuInflater.inflate(R.menu.bottom_nav_menu, popupMenu.menu)
        binding.navView.setupWithNavController(popupMenu.menu, navController)
    }
}


