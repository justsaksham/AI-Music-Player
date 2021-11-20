package com.example.aimusicplayer

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
        lateinit var itemsAll:ArrayList<String>
        lateinit var songList:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        songList=findViewById(R.id.songList)
        appExternalStoragePermission()
        getPermission()
    }
    fun getPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO),1)
    }
    fun appExternalStoragePermission(){
      // Dexter(this@MainActivity).withPermission()
        Dexter.withActivity(this@MainActivity)
            .withPermission( android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object:PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    displayAudioSongsName()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                     }

            })
            .check()

    }

    fun readOnlyAudioSongs(file: File):ArrayList<File>{
//        val arraylist:ArrayList<String> = arrayListOf()
//        val lis= arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DISPLAY_NAME)
//        val audioCursor= contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,lis,null,null,null)
//        if(audioCursor != null){
//            if(audioCursor.moveToFirst()){
//                do{
//                    val audioIndex = audioCursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
//
//                    arraylist.add(audioCursor!!.getString(audioIndex));
//                }while(audioCursor.moveToNext());
//            }
//        }
//        audioCursor!!.close();
//
        val arraylist=ArrayList<File>()

        val allfile = file.listFiles();
        for(individual in allfile){
            if(individual.isDirectory() && !individual.isHidden()){
                arraylist.addAll(readOnlyAudioSongs(individual))
            }
            else if (individual.name.endsWith(".mp3") || individual.name.endsWith(".acc")||individual.name.endsWith(".wac")||individual.name.endsWith(".wma")){
                    arraylist.add(individual)
            }
        }

        return arraylist

            }

    fun displayAudioSongsName():Unit{
        val audiosongs:ArrayList<File > = readOnlyAudioSongs(Environment.getExternalStorageDirectory())
        itemsAll= arrayListOf()
        for(i in 0 until audiosongs.size){
            itemsAll.add(audiosongs.get(i).name)
        }
        val adapter=ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,itemsAll)
        songList.adapter=adapter
        val mediaPlayer=MediaPlayer()
        songList.setOnItemClickListener{adapterView ,view,i,j ->
            val songName=songList.getItemAtPosition(i).toString()
            val intent = Intent(this@MainActivity,SmartPlayerActivity::class.java)

            val songsModel=SongsModel(audiosongs,songName,i)
            intent.putExtra("song",songsModel)
           // intent.putExtra("player",mediaPlayer)
//            intent.putExtra("name",songName);
//            intent.putExtra("position",i);
            startActivity(intent)

        }
    }

}
