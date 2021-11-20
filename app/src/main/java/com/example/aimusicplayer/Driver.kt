package com.example.aimusicplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore

class Driver {

    companion object{
        var mediaPlayer:MediaPlayer? =null
    fun getmediaPlayer(context:Context,uri:Uri):MediaPlayer?{
        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(context,uri)
            System.out.println("saksham")
        }
     else {
            mediaPlayer!!.pause()
            mediaPlayer!!.release()

            mediaPlayer=MediaPlayer.create(context,uri)
        }
        return mediaPlayer
   }
    }

}