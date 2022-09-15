package ru.denis.yandexmap.model

data class Address(
    val latitude: Double,
    val longitude: Double
) {
    override fun toString(): String {
        return "Address(latitude=$latitude, longitude=$longitude)"
    }
}
