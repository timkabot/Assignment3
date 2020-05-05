package com.app.hw3.presentation.petList

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hw3.R
import com.app.hw3.domain.entity.Pet
import com.app.hw3.presentation.adapter.ItemAdapter
import com.app.hw3.presentation.petDetail.DetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.Toothpick
import javax.inject.Inject


class PetListActivity : AppCompatActivity() {
    @Inject
    lateinit var petListVm: PetListViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toothpick.inject(this, Toothpick.openScope("app_scope"))

        progressBar = ProgressDialog(this)

        initObservers()
        initListeners()
        initRecyclerView()
        petListVm.initToken()
    }

    private fun initListeners() {

        petSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val text = petSpinner.selectedItem.toString()
                if (text == "Any") {
                    breedSpinner.adapter = null
                    petListVm.getAnimals()
                } else {
                    petListVm.getBreeds(text)
                    breedSpinner.adapter = null
                    progressBar.setCanceledOnTouchOutside(false)
                    progressBar.show()
                }
            }

        }
        breedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                petListVm.getAnimals(
                    petSpinner.selectedItem.toString(),
                    breedSpinner.selectedItem.toString()
                )

            }

        }
    }

    private fun initObservers() {
        petListVm.animalTypes.observe(this,
            Observer { animalTypes ->
                initAnimalTypesDropdown(animalTypes.map { it.name }.toMutableList())
            })

        petListVm.breeds.observe(this,
            Observer { breeds ->
                initAnimalBreeds(breeds.map { it.name }.toMutableList())
            })

        petListVm.animals.observe(this,
            Observer { animals -> updateRecyclerData(animals) })

        petListVm.progressStatus.observe(this,
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
        petSpinner.adapter = typesAdapter
    }

    private fun initAnimalBreeds(animalBreedNames: MutableList<String>) {
        val breedsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            baseContext,
            R.layout.spinner_item,
            animalBreedNames
        )
        breedSpinner.adapter = breedsAdapter
        progressBar.hide()
    }

    private fun initRecyclerView() {
        adapter = ItemAdapter(arrayListOf(), object : MyItemOnClickListener {
            override fun onClick(animal: Pet) {
                petListVm.lastDetailedAnimal = animal.copy()
                val intent = Intent(this@PetListActivity, DetailsActivity::class.java)
                startActivity(intent)
            }
        })

        val lManager = LinearLayoutManager(this)
        recycler.layoutManager = lManager
        recycler.adapter = adapter
    }

    private fun updateRecyclerData(animals: List<Pet>) {
        if (animals.isEmpty())
            Toast.makeText(this, "No items with this breed", Toast.LENGTH_SHORT).show()
        adapter.updateDate(animals.toList())
    }

}

interface MyItemOnClickListener {
    fun onClick(animal: Pet)
}
