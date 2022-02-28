package com.example.appcamaudiovideo

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcamaudiovideo.databinding.ActivityReproductorVideoBinding

class ReproductorVideo : AppCompatActivity() {

    private  lateinit var binding: ActivityReproductorVideoBinding
    private var mCurrentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReproductorVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME)
        }

        val mediaController = MediaController(this)
        mediaController.setMediaPlayer(binding.videoview)
        binding.videoview.setMediaController(mediaController)

    }

    override fun onStart() {
        super.onStart()

        initializePlayer()
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            binding.videoview.pause()
        }
    }

    override fun onStop() {
        super.onStop()

        releasePlayer()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(PLAYBACK_TIME, binding.videoview.getCurrentPosition())
    }

    private fun initializePlayer() {

        binding.bufferingTextview.setVisibility(VideoView.VISIBLE)

        val videoUri: Uri = getMedia(VIDEO_SAMPLE)!!
        binding.videoview.setVideoURI(videoUri)

        binding.videoview.setOnPreparedListener(
            MediaPlayer.OnPreparedListener {
                binding.bufferingTextview.setVisibility(VideoView.INVISIBLE)

                if (mCurrentPosition > 0) {
                    binding.videoview.seekTo(mCurrentPosition)
                } else {
                    binding.videoview.seekTo(1)
                }

                binding.videoview.start()
            })

        binding.videoview.setOnCompletionListener(
            MediaPlayer.OnCompletionListener {
                Toast.makeText(
                    this,
                    R.string.toast_message,
                    Toast.LENGTH_SHORT
                ).show()

                binding.videoview.seekTo(0)
            })
    }


    private fun releasePlayer() {
        binding.videoview.stopPlayback()
    }

    private fun getMedia(mediaName: String): Uri? {
        return if (URLUtil.isValidUrl(mediaName)) {
            Uri.parse(mediaName)
        } else {
            Uri.parse("android.resource://" + packageName.toString() +
                    "/raw/" + mediaName)
        }
    }

    companion object PLAY{
        val PLAYBACK_TIME = "play_time"
        val VIDEO_SAMPLE = "oceanopacifico"
    }
}