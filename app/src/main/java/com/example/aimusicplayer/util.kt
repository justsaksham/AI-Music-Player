package com.example.aimusicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast

class util {
    companion object{
        var mediaPlayer: MediaPlayer?=null
        fun getMediaPlayer(context: Context, uri: Uri): MediaPlayer?{
            if(mediaPlayer !=null){
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
                Toast.makeText(context,"saksham is doing", Toast.LENGTH_LONG).show()
            }
            mediaPlayer= MediaPlayer.create(context,uri);
            return mediaPlayer
        }
    }
}