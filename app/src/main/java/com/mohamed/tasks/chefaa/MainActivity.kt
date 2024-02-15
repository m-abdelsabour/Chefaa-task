package com.mohamed.tasks.chefaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tinify.Tinify
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Tinify.setKey(BuildConfig.TINY_KEY)
    }
}