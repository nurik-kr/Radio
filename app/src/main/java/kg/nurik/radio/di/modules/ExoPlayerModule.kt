package kg.nurik.radio.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import kg.nurik.radio.data.RadioStationsRepository
import kg.nurik.radio.utils.MediaPlayer
import kg.nurik.radio.utils.MediaPlayerImpl
import javax.inject.Singleton

@Module
class ExoPlayerModule {
    @Provides
    @Singleton
    fun provideExoPlayer(context: Context): MediaPlayer {
        return MediaPlayerImpl(context)
    }

    @Provides
    @Singleton
    fun provideRadioStationsRepository(): RadioStationsRepository {
        return RadioStationsRepository()
    }
}