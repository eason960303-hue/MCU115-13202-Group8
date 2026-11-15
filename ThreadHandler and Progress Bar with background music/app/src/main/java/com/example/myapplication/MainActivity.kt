package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val vm: WorkViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager2>(R.id.pager)
        val tabs = findViewById<TabLayout>(R.id.tabs)

        pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> StartFragment()
                1 -> ProgressFragment()
                else -> ResultFragment()
            }
        }

        TabLayoutMediator(tabs, pager) { tab, pos ->
            tab.text = when (pos) {
                0 -> getString(R.string.tab_start)
                1 -> getString(R.string.tab_progress)
                else -> getString(R.string.tab_result)
            }
        }.attach()

        vm.isMusicPlaying.observe(this) { shouldPlay ->
            if (shouldPlay) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.bg_music).apply {
                        isLooping = true
                    }
                }
                if (mediaPlayer?.isPlaying == false) {
                    mediaPlayer?.start()
                }
            } else {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                    mediaPlayer?.seekTo(0)
                }
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}