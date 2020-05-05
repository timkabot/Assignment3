package com.app.hw3.presentation.petDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.hw3.R
import com.app.hw3.presentation.petList.PetListViewModel
import com.app.hw3.utils.updatePhoto
import kotlinx.android.synthetic.main.activity_details.*
import toothpick.Toothpick
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var petListVm: PetListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope("app_scope"))
        setContentView(R.layout.activity_details)
        initData()
    }

    private fun initData() {
        val animal = petListVm.lastDetailedAnimal

        imageView2.updatePhoto(animal.photos?.get(0)?.small)

        description.text =  animal.description
        size.text =   animal.size
        url.text = animal.url
        name.text =  animal.name
        age.text =  animal.age
        gender.text =  animal.gender

    }
}