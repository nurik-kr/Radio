package kg.nurik.radio.di

import dagger.Component
import kg.nurik.radio.di.modules.AppModule
import kg.nurik.radio.di.modules.ExoPlayerModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ExoPlayerModule::class
    ]
)
interface RadioComponent {

    fun inject(injector: Injector)

}