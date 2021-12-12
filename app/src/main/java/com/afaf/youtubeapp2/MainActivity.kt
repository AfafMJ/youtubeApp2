package com.afaf.youtubeapp2

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.afaf.youtubeapp2.GridAdapter
import com.afaf.youtubeapp2.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    private val videos: Array<Array<String>> = arrayOf(
        arrayOf("Mohammed abdo-1", "7uyBebRfh94"),
        arrayOf("Mohammed abdo-2", "BOh534kz97k"),
        arrayOf("Talal Maddah", "8H3an-qzJfA"),
        arrayOf("Um kulthum", "i0b7MWIMBo4"),
        arrayOf("Mohammed abdo-3", "rfbmg8"),
        arrayOf("Talal Maddah-2", "3BrGd-m5_c4"),
        arrayOf("Talal Maddah3", "KVGFfd65TKQ"))
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var player: YouTubePlayer
    private var currentVideo = 0
    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()

        youTubePlayerView = findViewById(R.id.ytPlayer)
        gridView = findViewById(R.id.rvVideos)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                player.loadVideo(videos[currentVideo][1], 0f)
                gridView.adapter = GridAdapter(videos, player,this@MainActivity)
            }
        })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }



    private fun checkInternet(){
        if(!connectedToInternet()){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Internet Connection Not Found")
                .setPositiveButton("RETRY"){_, _ -> checkInternet()}
                .show()
        }
    }

    private fun connectedToInternet(): Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if(activeNetwork?.isConnectedOrConnecting == true){
            //do something with the network
        }
        else
        {
            println("Not Connected To The Internet")
        }
        return activeNetwork?.isConnectedOrConnecting == true
    }
}