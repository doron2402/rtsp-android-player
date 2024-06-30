package com.unit1337.rtsp2


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {
    private lateinit var rtspUrlEditText: EditText
    private lateinit var connectButton: Button
    private lateinit var playerView: PlayerView
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rtspUrlEditText = findViewById(R.id.rtsp_url)
        connectButton = findViewById(R.id.connect_button)
        playerView = findViewById(R.id.player_view)

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer

        connectButton.setOnClickListener {
            var rtspUrl = rtspUrlEditText.text.toString()
            if (rtspUrlEditText.text.toString() == "") {
//                 rtspUrl = "rtsp://51.17.227.45:5555"
//                rtspUrl = "rtsp://82.166.176.26:8554/bla"
//                rtspUrl = "rtsp://51.17.227.45:5555/av0_0"
                rtspUrl = "rtsp://51.17.227.45:5555/av0_1"
//                 rtspUrl = "rtsp://fake.kerberos.io/stream"
            }
//            val rtspUrl = "rtsp://fake.kerberos.io/stream"
            playRtspStream(rtspUrl)
        }
    }

    @OptIn(UnstableApi::class)
    private fun playRtspStream(rtspUrl: String) {
        val mediaItem = MediaItem.fromUri(rtspUrl)
        val factory = RtspMediaSource.Factory()
        exoPlayer?.setMediaSource(factory.createMediaSource(mediaItem))
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.release()
        exoPlayer = null
    }
}