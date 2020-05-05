package com.app.hw3.presentation.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.hw3.R
import com.app.hw3.domain.entity.Pet
import com.app.hw3.presentation.petList.MyItemOnClickListener
import com.app.hw3.utils.updatePhoto
import com.app.hw3.utils.inflate

class ItemAdapter(private val items: ArrayList<Pet>, val clickListener: MyItemOnClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.imageView)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val age: TextView = itemView.findViewById(R.id.age)
        private val breed: TextView = itemView.findViewById(R.id.breed)

        init {
            itemView.setOnClickListener { clickListener.onClick(items[layoutPosition]) }
        }

        fun bind(animal: Pet) {
            animal.photos?.let { photoList ->
                if (photoList.size > 0) {
                    photoList[0].small?.let {
                        photo.updatePhoto(it)
                    }
                }
            }

            name.text = animal.name
            description.text = animal.description
            age.text = animal.age
            breed.text = animal.breed

        }
    }

    fun notifyChanges(oldList: ArrayList<Pet>, newList: ArrayList<Pet>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newList.size
        })
        diff.dispatchUpdatesTo(this)
    }

    fun updateDate(newItems: List<Pet>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView = parent.inflate(R.layout.card_animal_short_description, false)
        return ItemViewHolder(inflatedView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}