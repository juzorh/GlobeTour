package com.juzor.globetour.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.juzor.globetour.R
import com.juzor.globetour.model.City

class CityAdapter(
    val context: Context,
    var cityList: ArrayList<City>
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.list_item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder, position: Int) {
        val city = cityList[position]
        cityViewHolder.bind(city, position)
    }

    override fun getItemCount() : Int = cityList.size

    inner class CityViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

        private var currentPosition: Int = -1
        private var currentCity: City? = null

        // Drawable Resources
        private val icFavoriteFilledImage = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.ic_favorite_filled,
            null
        )
        private val icFavoriteBorderedImage = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.ic_favorite_bordered,
            null
        )

        private val txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private val imvCityImage= itemView.findViewById<ImageView>(R.id.imv_city)
        //private val imvDelete   = itemView.findViewById<ImageView>(R.id.imv_delete)
        private val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)

        fun bind(city: City, position: Int){
            if (city.isFavorite)
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
            else
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)

            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)


            this.currentPosition = position
            this.currentCity = city
        }
    }

}