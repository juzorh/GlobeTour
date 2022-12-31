package com.juzor.globetour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.juzor.globetour.databinding.ActivityFullPictureBinding

class FullPicture : AppCompatActivity() {
    private lateinit var binding: ActivityFullPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullImage()
    }

    private fun fullImage(){
        val cityId = intent.getIntExtra("CITY_IMAGE", 0)

        Toast.makeText(this, cityId.toString(), Toast.LENGTH_LONG).show()
        binding.imvFullImage.setImageResource(cityId)
    }
}