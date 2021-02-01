package kg.nurik.radio.ui.favourite

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kg.nurik.radio.R
import kg.nurik.radio.data.RadioStations
import kg.nurik.radio.data.repository.RadioStationsRepository
import kg.nurik.radio.data.service.RadioService
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.view_player.view.*
import timber.log.Timber

class FavouriteFragment:Fragment(R.layout.fragment_favourite) {

    private var radioService: RadioService? = null
    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(context, RadioService::class.java) }
    private var isBound = false
    private var radio: RadioStations? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupServiceConnection()
        setupRadio()
        setupListeners()
    }

    private fun setupRadio() {
        radioService?.getActiveStation()?.observe(viewLifecycleOwner, Observer {
            tvNameStations.text = it.name
        })
    }

    private fun setupServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(                        //потом сюда прилетает
                name: ComponentName?, service: IBinder?
            ) {          //когда подключимся
                isBound = true
                radioService = (service as RadioService.RadioBinder).getService() //доступ к сервису
                setupRadio()
                radioService?.chooseRadio(
                    radio ?: RadioStationsRepository.radioList[0]
                )//если 0 то пусть выберет нулевой элемент
                viewPlayer.imgPause.isActivated = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {  //когда отключимся
                Timber.d("onServiceDisconnected")
                isBound = false
                viewPlayer.imgPause.isActivated = false
            }
        }
    }

    private fun setupListeners() {
        viewPlayer.imgPause.setOnClickListener {
            if (it.isActivated) {
                requireContext().unbindService(serviceConnection)
                viewPlayer.imgPause.isActivated = false
                isBound = false
            } else {
                requireContext().bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
                viewPlayer.imgPause.isActivated = true
                isBound = true
            }
        }
        viewPlayer.imgNext.setOnClickListener {
            radioService?.nextRadio()
        }
        viewPlayer.imgBack.setOnClickListener {
            radioService?.prevRadio()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        requireContext().unbindService(serviceConnection)
    }
}