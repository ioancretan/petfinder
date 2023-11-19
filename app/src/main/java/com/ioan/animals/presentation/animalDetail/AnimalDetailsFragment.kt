package com.ioan.animals.presentation.animalDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.namespace.R
import com.example.namespace.databinding.FragmentAnimalDetailsBinding
import com.ioan.animals.presentation.animalList.AnimalsFragment

class AnimalDetailsFragment : Fragment(R.layout.fragment_animal_details) {
    private lateinit var binding: FragmentAnimalDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animalName.text = getStringFromBundle(AnimalsFragment.NAME)
        binding.animalBreed.text = getStringFromBundle(AnimalsFragment.BREED)
        binding.animalSize.text = getStringFromBundle(AnimalsFragment.SIZE)
        binding.animalGender.text = getStringFromBundle(AnimalsFragment.GENDER)
        binding.animalStatus.text = getStringFromBundle(AnimalsFragment.STATUS)
        binding.animalDistance.text = getStringFromBundle(AnimalsFragment.DISTANCE)
    }

    private fun getStringFromBundle(key: String): String =
        arguments?.getString(key)
            ?: "Error"
}