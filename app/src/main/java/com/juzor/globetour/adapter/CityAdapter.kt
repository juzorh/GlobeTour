package com.juzor.globetour.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.juzor.globetour.FullPicture
import com.juzor.globetour.R
import com.juzor.globetour.model.City
import com.juzor.globetour.model.VacationSpots

class CityAdapter(
    val context: Context,
    var cityList: ArrayList<City>
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.grid_item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(cityViewHolder: CityViewHolder, position: Int) {
        val city = cityList[position]
        cityViewHolder.bind(city, position)
        cityViewHolder.setOnClickListener()
    }

    override fun getItemCount() : Int = cityList.size

    inner class CityViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView), View.OnClickListener {

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
        private val imvDelete   = itemView.findViewById<ImageView>(R.id.imv_delete)
        private val imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)
        private val rootCardView = itemView.findViewById<CardView>(R.id.root_card_view)

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

        fun setOnClickListener() {
            imvFavorite.setOnClickListener(this@CityViewHolder)
            imvDelete.setOnClickListener(this@CityViewHolder)
            rootCardView.setOnClickListener(this@CityViewHolder)
        }

        override fun onClick(v: View?) {
            when (v?.id){
                R.id.imv_favorite -> addToFavorite()
                R.id.imv_delete -> deleteItem()
                R.id.root_card_view -> fullScreenDisplay()
            }
        }

        private fun fullScreenDisplay() {
            Toast.makeText(context, "See full picture", Toast.LENGTH_LONG).show()

            val id = currentPosition

            currentCity?.let {
                val cityImage = it.imageId
                val isFavorite = it.isFavorite

                val intent = Intent(context, FullPicture::class.java)
                intent.putExtra("CITY_ID", id)
                intent.putExtra("CITY_IMAGE", cityImage)
                intent.putExtra("isFavorite", isFavorite)

                context.startActivity(intent)
            }
        }

        private fun deleteItem() {
            cityList.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
            notifyItemRangeChanged(currentPosition, cityList.size)

            // Removes the deleted city from the favorite list
            VacationSpots.favoriteCityList.remove(currentCity)
        }

        private fun addToFavorite() {
            currentCity?.let {
                it.isFavorite = !it.isFavorite // toggle 'isFavorite' boolean

                // Change icon and add to favorite list.
                if (it.isFavorite){
                    imvFavorite.setImageDrawable(icFavoriteFilledImage)
                    VacationSpots.favoriteCityList.add(it)
                }else{
                    // Change icon and remove from favorite list
                    imvFavorite.setImageDrawable(icFavoriteBorderedImage)
                    VacationSpots.favoriteCityList.remove(it)
                }
            }
        }
    }

}