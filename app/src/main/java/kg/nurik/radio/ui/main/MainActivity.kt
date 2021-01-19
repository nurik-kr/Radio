package kg.nurik.radio.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.nurik.radio.data.RadioStations
import kg.nurik.radio.data.RadioStationsRepository
import kg.nurik.radio.data.service.RadioService
import kg.nurik.radio.databinding.ActivityMainBinding
import kg.nurik.radio.ui.RadioAdapter
import kg.nurik.radio.utils.viewBinding
import kg.nurik.radio.utils.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel(MainViewModel::class) //ext fun to helper

    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(this, RadioService::class.java) }
    private var radioService: RadioService? = null
    private var radio: RadioStations? = null

    private val adapter by lazy {
        RadioAdapter() {
            radio = it
            bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
            radioService?.chooseRadio(it)
            viewModel.isBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupServiceConnection()
        setupListeners()
        setupRV()
    }

    private fun setupRadio() {
        radioService?.getActiveStation()?.observe(this, Observer {
            binding.viewPlayer.playName.text = it.name
        })
    }

    private fun setupRV() {
        binding.RvRadio.adapter = adapter
        adapter.update(RadioStationsRepository.radioList)
    }

    private fun setupServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(                        //потом сюда прилетает
                name: ComponentName?, service: IBinder?
            ) {          //когда подключимся
                viewModel.isBound = true
                radioService = (service as RadioService.RadioBinder).getService() //доступ к сервису
                setupRadio()
                radioService?.chooseRadio(radio ?: RadioStationsRepository.radioList[0])//если 0 то пусть выберет нулевой элемент
                binding.viewPlayer.imgPause.isActivated = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {  //когда отключимся
                Timber.d("onServiceDisconnected")
                viewModel.isBound = false
                binding.viewPlayer.imgPause.isActivated = false
            }
        }
    }

    private fun setupListeners() {
        binding.viewPlayer.imgPause.setOnClickListener {
            if (it.isActivated) {
                unbindService(serviceConnection)
                binding.viewPlayer.imgPause.isActivated = false
                viewModel.isBound = false
            } else {
                bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
                binding.viewPlayer.imgPause.isActivated = true
                viewModel.isBound = true
            }
        }
        binding.viewPlayer.imgNext.setOnClickListener {
//            viewModel.nextStation()
            radioService?.nextRadio()
        }
        binding.viewPlayer.imgBack.setOnClickListener {
//            viewModel.backStation()
            radioService?.prevRadio()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        unbindService(serviceConnection)
    }
}