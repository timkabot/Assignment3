package com.app.assignment3.presentation.detailsScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.assignment3.R
import com.app.assignment3.presentation.mainScreen.MainViewModel
import com.app.assignment3.utils.downloadImage
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.ext.android.inject

class DetailsActivity : AppCompatActivity(){
    private val mainVm: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initData()
    }
    private fun initData(){
        val animal = mainVm.lastDetailedAnimal

        imageView2.downloadImage(animal.photos?.get(0)?.small)

        name.text = "Name: " + animal.name
        age.text = "Age: " + animal.age
        gender.text ="Gender: " + animal.gender
        description.text ="Description: " + animal.description
        size.text ="Size: " + animal.size
        url.text ="Url: " + animal.url
    }
}