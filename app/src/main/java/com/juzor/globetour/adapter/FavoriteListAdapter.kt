package com.juzor.globetour.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.juzor.globetour.R
import com.juzor.globetour.model.City

class FavoriteListAdapter(
    val context: Context,
    var favoriteCityList: MutableList<City>
) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteCityViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCityViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.list_item_favorite, parent, false)
        return FavoriteCityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteCityViewHolder, position: Int) {
        val favoriteCity = favoriteCityList[position]
        holder.bind(favoriteCity)
    }

    override fun getItemCount(): Int = favoriteCityList.size

    inner class FavoriteCityViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){

        private val imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)

        fun bind(favoriteCity: City) {
            imvCityImage.setImageResource(favoriteCity.imageId)
            txvCityName.text = favoriteCity.name
        }

    }

}