package ru.denis.yandexmap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoordinatesViewModel : ViewModel() {
    private val _coordinates: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val coordinates: LiveData<Pair<Double, Double>> = _coordinates

    fun setCoordinates(
        latitude: Double,
        longitude: Double
    ) {
        _coordinates.postValue(Pair(latitude, longitude))
    }
}