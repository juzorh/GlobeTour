package com.juzor.globetour.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.juzor.globetour.adapter.FavoriteListAdapter
import com.juzor.globetour.databinding.FragmentFavoriteBinding
import com.juzor.globetour.model.City
import com.juzor.globetour.model.VacationSpots
import java.util.Collections

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favoriteCityList: ArrayList<City>
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val context = requireContext()

        favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
        recyclerView = binding.favouriteListRecyclerview
        recyclerViewAdapter = FavoriteListAdapter(context, favoriteCityList)

        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            targetViewHolder: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.adapterPosition
            val toPosition = targetViewHolder.adapterPosition

            Collections.swap(favoriteCityList, fromPosition, toPosition)
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            val deletedCity = favoriteCityList[position]
            deleteItem(position)

            // Changes the deleted city's "isFavorite" attr to false
            updateCityList(deletedCity, false)

            Snackbar.make(recyclerView, "City Remove", Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    undoDelete(position, deletedCity)
                    updateCityList(deletedCity, true)
                }
                .show()
        }

    })

    private fun undoDelete(position: Int, deletedCity: City) {
        favoriteCityList.add(position, deletedCity)

        recyclerViewAdapter.apply {
            notifyItemInserted(position)
            notifyItemRangeChanged(position, favoriteCityList.size)
        }
    }

    private fun updateCityList(deletedCity: City, isFavorite: Boolean) {
        val cityList = VacationSpots.cityList

        cityList?.let {
            val position = it.indexOf(deletedCity)
            it[position].isFavorite = isFavorite
        }
    }

    private fun deleteItem(position: Int) {
        favoriteCityList.removeAt(position)
        recyclerViewAdapter.apply {
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoriteCityList.size)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}