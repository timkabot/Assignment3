package com.app.assignment3.presentation.mainScreen

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.assignment3.R
import com.app.assignment3.domain.entity.Animal
import com.app.assignment3.presentation.detailsScreen.DetailsActivity
import com.app.assignment3.presentation.global.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val mainVm: MainViewModel by inject()
    private lateinit var adapter: ItemAdapter
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressDialog(this)

        initObservers()
        initListeners()
        initRecyclerView()
        mainVm.initToken()
    }

    private fun initListeners() {
        animalTypeDropdown.setOnItemClickListener { _, _, _, _ ->
            val text = animalTypeDropdown.text.toString()
            if(text == "Any"){
                breedsDropDown.setText("Any")
                breedsDropDown.setAdapter(null)

                mainVm.getAnimals()
            }
            else {
                mainVm.getBreeds(text)
                breedsDropDown.setText("")
                breedsDropDown.setAdapter(null)
                progressBar.setCanceledOnTouchOutside(false)
                progressBar.show()
            }

        }
        breedsDropDown.setOnItemClickListener { _, _, _, _ ->
            mainVm.getAnimals(animalTypeDropdown.text.toString(), breedsDropDown.text.toString())
        }
    }

    private fun initObservers() {
        mainVm.animalTypes.observe(this,
            Observer { animalTypes ->
                initAnimalTypesDropdown(animalTypes.map { it.name }.toMutableList())
            })

        mainVm.breeds.observe(this,
            Observer { breeds ->
                initAnimalBreeds(breeds.map { it.name }.toMutableList())
            })

        mainVm.animals.observe(this,
            Observer { animals -> updateRecyclerData(animals) })

        mainVm.progressStatus.observe(this,
            Observer { status ->
                when (status) {
                    "show" -> {
                        progressBar.show()
                        progressBar.setCanceledOnTouchOutside(false)
                    }
                    "hide" -> {
                        progressBar.hide()
                    }
                }

            })
    }

    private fun initAnimalTypesDropdown(animalTypeNames: MutableList<String>) {
        animalTypeNames.add("Any")
        val typesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            baseContext,
            R.layout.spinner_item,
            animalTypeNames
        )
        animalTypeDropdown.setAdapter(typesAdapter)
    }

    private fun initAnimalBreeds(animalBreedNames: MutableList<String>) {
        val breedsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            baseContext,
            R.layout.spinner_item,
            animalBreedNames
        )
        breedsDropDown.setAdapter(breedsAdapter)
        progressBar.hide()
    }

    private fun initRecyclerView() {
        adapter = ItemAdapter(arrayListOf(), object : MyItemOnClickListener {
            override fun onClick(animal: Animal) {
                println("click")
                mainVm.lastDetailedAnimal = animal.copy()
                println(mainVm.lastDetailedAnimal)
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                startActivity(intent)
            }
        })
        val lManager = LinearLayoutManager(this)
        recycler.layoutManager = lManager
        recycler.adapter = adapter
    }

    private fun updateRecyclerData(animals: List<Animal>) {
        if (animals.isEmpty())
            Toast.makeText(this, "No items with this breed", Toast.LENGTH_SHORT).show()
        adapter.updateDate(animals.toList())
    }

}

interface MyItemOnClickListener {
    fun onClick(animal: Animal)
}
