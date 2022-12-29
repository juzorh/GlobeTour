package com.juzor.globetour.ui.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juzor.globetour.adapter.CityAdapter
import com.juzor.globetour.databinding.FragmentCityListBinding
import com.juzor.globetour.model.VacationSpots

class CityList : Fragment() {

    private var _binding: FragmentCityListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)

        setupRecyclerView(view)

        return binding.root
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()

        val cityAdapter = VacationSpots.cityList?.let {
            CityAdapter(context, it)
        }

        binding.cityRecyclerView.apply {
            adapter = cityAdapter
            setHasFixedSize(true) //if the data size doesn't change at runtime
            //layoutManager = GridLayoutManager(context, 2)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}