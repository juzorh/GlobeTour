package com.juzor.globetour.ui.citylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CityListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is City List Fragment"
    }
    val text: LiveData<String> = _text
}