package com.naw.image_ine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.naw.image_ine.ui.ImagesViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imagesViewModel: ImagesViewModel by viewModels()

        (application as ImageIneApplication).appComponent.inject(this)
        (application as ImageIneApplication).appComponent.inject(imagesViewModel)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}
