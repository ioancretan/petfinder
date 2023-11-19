package com.ioan.animals.presentation.animalList


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.databinding.ItemAnimalBinding
import com.ioan.animals.data.animalList.Animal
import com.ioan.animals.utils.getProgressDrawable


class CountryListAdapter(
    private var animals: ArrayList<Animal>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {


    fun updateAnimals(newAnimals: List<Animal>) {
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAnimalBinding.inflate(inflater, parent, false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount() = animals.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(animals[position])
        holder.itemView.setOnClickListener { onClickListener.onClick(animals[position]) }
    }

    class CountryViewHolder(private val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: Animal) {
            binding.item = animal
        }
    }
}

class OnClickListener(val clickListener: (animal: Animal) -> Unit) {
    fun onClick(animal: Animal) = clickListener(animal)
}