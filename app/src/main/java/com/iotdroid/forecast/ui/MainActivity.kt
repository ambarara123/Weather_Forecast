package com.iotdroid.forecast.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.iotdroid.forecast.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    private lateinit var navController : NavController

    companion object{
        var a = 0
    }

   // public static int a = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp( navController,null)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }




}
