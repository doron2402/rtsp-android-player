package com.unit1337.rtsp2

// RTSP URLS
//  "rtsp://51.17.227.45:5555"
//  "rtsp://82.166.176.26:8554/bla"
//  "rtsp://51.17.227.45:5555/av0_0"
//  "rtsp://51.17.227.45:5555/av0_1"
//  "rtsp://fake.kerberos.io/stream"
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class MainActivity : AppCompatActivity() {
    private lateinit var rtspUrlEditText: EditText
    private lateinit var connectButton: Button
    private lateinit var playerView: PlayerView
    private var exoPlayer: ExoPlayer? = null

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rtspUrlEditText = findViewById(R.id.rtsp_url)
        connectButton = findViewById(R.id.connect_button)
        playerView = findViewById(R.id.player_view)

        // Create a DefaultTrackSelector with preferred video codecs and disabled audio
        val trackSelector = DefaultTrackSelector(this).apply {
            setParameters(buildUponParameters()
                .setPreferredVideoMimeType(MimeTypes.VIDEO_H264)
//                .setPreferredVideoMimeType(MimeTypes.VIDEO_H265)
                .setDisabledTrackTypes(setOf(C.TRACK_TYPE_AUDIO))
            )
        }

        exoPlayer = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()

        playerView.player = exoPlayer

        connectButton.setOnClickListener {
            var rtspUrl = rtspUrlEditText.text.toString()
            if (rtspUrl.isEmpty()) {
                rtspUrl = "rtsp://51.17.227.45:5555/av0_0"
            }
            playRtspStream(rtspUrl)
        }

        // Add player event listener for logging errors
        exoPlayer?.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.e("ExoPlayer", "Player error: ${error.message}")
            }
        })
    }

    @OptIn(UnstableApi::class)
    private fun playRtspStream(rtspUrl: String) {
        val mediaItem = MediaItem.fromUri(rtspUrl)
        val factory = RtspMediaSource.Factory()
        val mediaSource = factory.createMediaSource(mediaItem)

        exoPlayer?.setMediaSource(mediaSource)
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.release()
        exoPlayer = null
    }
}

private fun Player.addListener(any: Any) {

}
