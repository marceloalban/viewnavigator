package com.alban.viewnavigator.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alban.viewnavigator.app.databinding.ActivityExampleBinding

class ActivityExample : AppCompatActivity() {
    private lateinit var activityExampleBinding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExampleBinding = ActivityExampleBinding.inflate(layoutInflater)

        setContentView(activityExampleBinding.root)
    }
}