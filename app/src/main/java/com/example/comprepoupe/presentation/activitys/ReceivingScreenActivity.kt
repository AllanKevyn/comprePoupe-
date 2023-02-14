package com.example.comprepoupe.presentation.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.comprepoupe.R

class ReceivingScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving_screen)
        supportActionBar?.hide()
        setup()
    }
    private fun setup(){
        setupFragment()
    }
    private fun setupFragment(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idFragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}