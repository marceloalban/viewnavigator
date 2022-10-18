package com.alban.viewnavigator.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alban.viewnavigator.ViewNavigatorWindow
import com.alban.viewnavigator.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(activityMainBinding.root)

        activityMainBinding.buttonNavigator.setOnClickListener {
            ViewNavigatorWindow(activityMainBinding.root).show()
        }

        activityMainBinding.buttonActivityExample.setOnClickListener {
            startActivity(Intent(this, ActivityExample::class.java))
        }
    }
}