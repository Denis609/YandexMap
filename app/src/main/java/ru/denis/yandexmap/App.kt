package ru.denis.yandexmap

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    companion object {
        const val apiKey: String = "c475467b-3179-42a2-9bde-0760f8f6bf9d"
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(apiKey)
    }
}