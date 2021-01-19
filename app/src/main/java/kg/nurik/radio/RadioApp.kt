package kg.nurik.radio

import android.app.Application
import androidx.viewbinding.BuildConfig
import kg.nurik.radio.di.DaggerRadioComponent
import kg.nurik.radio.di.RadioComponent
import kg.nurik.radio.di.modules.AppModule
import timber.log.Timber

class RadioApp : Application() {

    lateinit var daggerComponent: RadioComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        daggerComponent = DaggerRadioComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var app: RadioApp
    }
}