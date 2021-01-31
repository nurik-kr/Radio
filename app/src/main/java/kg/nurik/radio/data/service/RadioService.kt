package kg.nurik.radio.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import kg.nurik.radio.data.RadioStations
import kg.nurik.radio.data.enum.NotificationClickTypes
import kg.nurik.radio.di.inject
import kg.nurik.radio.utils.NotificationHelper
import timber.log.Timber

class RadioService : Service() {

    private val binder by lazy { RadioBinder() } //внутри binder лежит экземпляр класса RadioBinder
    private val player by inject { mediaPlayer }
    private val radioStations by inject { radioStations }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleClicks(intent?.action)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleClicks(type: String?) {
        when (type) {
            NotificationClickTypes.PREV.name -> {
                prevRadio()
            }
            NotificationClickTypes.PLAY.name -> {

            }
            NotificationClickTypes.NEXT.name -> {
                nextRadio()
            }
        }
    }

    private fun play(station: RadioStations?) {
        station?.stations?.let { player.play(it) }
    }

    fun nextRadio() {
        play(radioStations.nextStation())
    }

    fun prevRadio() {
        play(radioStations.backStation())
    }

    fun pauseRadio() {
        play(station = null)
    }

    fun getActiveStation(): MutableLiveData<RadioStations> {
        return radioStations.radioLiveData
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind")
        return binder //и тут возвращаем
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(1, NotificationHelper.createNotification(applicationContext))
    }

    inner class RadioBinder : Binder() { //return RadioService сам экземпляр класса
        fun getService() = this@RadioService
    }

    fun chooseRadio(station: RadioStations?) {
        radioStations.radioLiveData.postValue(station)
        play(station)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Timber.d("onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.pause()
        stopSelf()
    }
}