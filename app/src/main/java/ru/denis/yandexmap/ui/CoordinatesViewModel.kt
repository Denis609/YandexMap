package ru.denis.yandexmap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.denis.yandexmap.model.Address

class CoordinatesViewModel : ViewModel() {
    private val _coordinates: MutableLiveData<Address> = MutableLiveData()
    val coordinates: LiveData<Address> = _coordinates

    fun setCoordinates(
        latitude: Double,
        longitude: Double
    ) {
        _coordinates.postValue(Address(latitude, longitude))
    }
}