package kg.nurik.radio.di

import kg.nurik.radio.RadioApp
import kg.nurik.radio.data.RadioStationsRepository
import kg.nurik.radio.utils.MediaPlayer
import javax.inject.Inject

inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector().block() }

class Injector {

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var radioStations: RadioStationsRepository

    init {
        RadioApp.app.daggerComponent.inject(this)
    }
}