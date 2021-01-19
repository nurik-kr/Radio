package kg.nurik.radio.utils

import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import timber.log.Timber

interface MediaPlayer {
    fun play(url: String)
    fun pause()
    fun stop()
    fun getExoPlayer(): ExoPlayer
}

class MediaPlayerImpl(private val context: Context) : MediaPlayer, Player.EventListener {

    private lateinit var exoMediaPlayer: SimpleExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private lateinit var transportControl: MediaControllerCompat.TransportControls

    init {
        initializeMediaSession()
        initializePlayer()
    }

    override fun play(url: String) {
        val dataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "Radio"))

        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory()) //работа с медиаСтримами
            .createMediaSource(Uri.parse(url))

        exoMediaPlayer.prepare(mediaSource)
        exoMediaPlayer.playWhenReady = true
    }

    override fun pause() {
        exoMediaPlayer.playWhenReady = false
    }

    override fun stop() {
        exoMediaPlayer.stop()
    }

    override fun getExoPlayer(): ExoPlayer {
        return exoMediaPlayer
    }

    private fun initializePlayer() {
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectorFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectorFactory)

        exoMediaPlayer =
            ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        exoMediaPlayer.addListener(this)
    }

    private fun initializeMediaSession() {
        mediaSession = MediaSessionCompat(context, "Radio")
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mediaSession.setMediaButtonReceiver(null)

        stateBuilder = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_FAST_FORWARD or
                        PlaybackStateCompat.ACTION_REWIND
            )

        mediaSession.setPlaybackState(stateBuilder.build())
        mediaSession.setCallback(callback)
        mediaSession.isActive = true
    }

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            super.onPlay()
            Timber.d("onPlay")
        }

        override fun onPause() {
            super.onPause()
            Timber.d("onPause")
        }

        override fun onStop() {
            super.onStop()
            Timber.d("onStop")
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        Timber.d("onPlaybackParametersChanged")    }

    override fun onSeekProcessed() {
        Timber.d("onSeekProcessed")    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
        Timber.d("onTracksChanged")    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Timber.d("onPlayerError")    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Timber.d("onLoadingChanged")    }

    override fun onPositionDiscontinuity(reason: Int) {
        Timber.d("onPositionDiscontinuity")    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        Timber.d("onRepeatModeChanged")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        Timber.d("onShuffleModeEnabledChanged")
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        Timber.d("onTimelineChanged")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Timber.d("onPlayerStateChanged")
    }
}