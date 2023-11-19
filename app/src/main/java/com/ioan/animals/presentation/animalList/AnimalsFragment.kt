package com.ioan.animals.presentation.animalList

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.namespace.R
import com.example.namespace.databinding.FragmentAnimalsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ioan.animals.main.AnimalsApplication
import com.ioan.animals.main.MainViewModelFactory
import javax.inject.Inject


class AnimalsFragment : Fragment(R.layout.fragment_animals) {

    private lateinit var animalsViewModel: AnimalsViewModel
    private lateinit var countriesAdapter: CountryListAdapter

    private lateinit var binding: FragmentAnimalsBinding

    lateinit var mFusedLocationClient : FusedLocationProviderClient

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as AnimalsApplication).getAnimalsComponent.inject(this)
        animalsViewModel = ViewModelProvider(this, mainViewModelFactory)[AnimalsViewModel::class.java]

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLocation()
        countriesAdapter = CountryListAdapter(arrayListOf(), OnClickListener {

            val bundle = Bundle()
            bundle.putString(NAME, it.name)
            bundle.putString(BREED, it.breeds.primary)
            bundle.putString(SIZE, it.size)
            bundle.putString(GENDER, it.gender)
            bundle.putString(STATUS, it.status)
            bundle.putString(DISTANCE, it.distance)

            findNavController()
                .navigate(R.id.action_root_to_content_page, bundle)
        })

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
        binding.swipeRefreshLayout.viewTreeObserver.addOnScrollChangedListener {
            val viewItem = binding.swipeRefreshLayout.getChildAt(binding.swipeRefreshLayout.childCount - 1) as View
            val diff: Int = viewItem.bottom - (binding.swipeRefreshLayout.height + binding.swipeRefreshLayout
                .scrollY)
            if (diff == 0) {
               animalsViewModel.fetchAnimalsNextPage()
            }
        }
        observeViewModel()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location = task.result
                    animalsViewModel.latitude = location.latitude
                    animalsViewModel.longitude = location.longitude
                    animalsViewModel.fetchAnimalsNextPage()
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun observeViewModel() {

        animalsViewModel.animalsLiveData.observe(viewLifecycleOwner) { animals ->
            animals?.let {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateAnimals(it)
            }
        }
        animalsViewModel.countryLoadError.observe(viewLifecycleOwner) { isError ->
            isError?.let { binding.listError.visibility = if (it) View.VISIBLE else View.GONE }
        }

        animalsViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "cannot load animals cuz you did not provide location",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "cannot load animals cuz you did not provide location",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object{
        private val LOCATION_PERMISSION_ID = 1000
        val NAME = "NAME"
        val BREED = "breed"
        val SIZE = "SIZE"
        val GENDER = "GENDER"
        val STATUS = "STATUS"
        val DISTANCE = "DISTANCE"
    }
}