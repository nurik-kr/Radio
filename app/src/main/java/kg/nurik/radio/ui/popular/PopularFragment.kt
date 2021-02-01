package kg.nurik.radio.ui.popular

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kg.nurik.radio.R
import kg.nurik.radio.data.RadioStations
import kg.nurik.radio.data.repository.RadioStationsRepository
import kg.nurik.radio.data.service.RadioService
import kg.nurik.radio.ui.RadioAdapter
import kg.nurik.radio.utils.viewModel
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.view_player.view.*
import timber.log.Timber

class PopularFragment : Fragment(R.layout.fragment_popular), SeekBar.OnSeekBarChangeListener {

    private val viewModel by viewModel(PopularViewModel::class)
//    private val binding by viewBinding(::bind)

    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(context, RadioService::class.java) }
    private var radioService: RadioService? = null
    private var radio: RadioStations? = null

    private val adapter by lazy {
        RadioAdapter() {
            radio = it
            requireActivity().bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
            radioService?.chooseRadio(it)
            viewModel.isBound = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSeekBar()
        setupServiceConnection()
        setupListeners()
        setupRV()
    }

    private fun setupSeekBar() {
        seekBar.setOnSeekBarChangeListener(this)
    }

    private fun setupRadio() {
        radioService?.getActiveStation()?.observe(viewLifecycleOwner, Observer {
            tvTextStations.text = it.name
            tvDescStations.text = it.desc
        })
    }

    private fun setupRV() {
        RvRadio.layoutManager = GridLayoutManager(context, 2)
        RvRadio.adapter = adapter
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
                radioService?.chooseRadio(
                    radio ?: RadioStationsRepository.radioList[0]
                )//если 0 то пусть выберет нулевой элемент
                viewPlayer.imgPause.isActivated = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {  //когда отключимся
                Timber.d("onServiceDisconnected")
                viewModel.isBound = false
                viewPlayer.imgPause.isActivated = false
                requireActivity().unbindService(serviceConnection)
            }
        }
    }

    private fun setupListeners() {
        viewPlayer.imgPause.setOnClickListener {
            if (it.isActivated) {
                requireActivity().unbindService(serviceConnection)
                viewPlayer.imgPause.isActivated = false
                viewModel.isBound = false
            } else {
                requireActivity().bindService(intentService, serviceConnection, Context.BIND_AUTO_CREATE)
                viewPlayer.imgPause.isActivated = true
                viewModel.isBound = true
            }
        }
        viewPlayer.imgNext.setOnClickListener {
            radioService?.nextRadio()
        }
        viewPlayer.imgBack.setOnClickListener {
            radioService?.prevRadio()
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvSoundGrom.text = seekBar.progress.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}


}