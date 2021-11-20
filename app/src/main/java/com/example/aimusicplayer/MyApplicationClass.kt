package com.example.aimusicplayer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
// if add this in manifest file then only it act as applicationContext and singleton class
class MyApplicationClass:Application() {

 var mediaPlayer: MediaPlayer?=null
    fun getMediaPlayer(context: Context,uri: Uri):MediaPlayer?{
        if(mediaPlayer !=null){
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            Toast.makeText(context,"saksham is doing", Toast.LENGTH_LONG).show()
        }
        mediaPlayer=MediaPlayer.create(context,uri);
//       mediaPlayer= MediaPlayer()
//        mediaPlayer!!.setDataSource(uri.path!!)
//        mediaPlayer!!.start()
        return mediaPlayer
    }

}