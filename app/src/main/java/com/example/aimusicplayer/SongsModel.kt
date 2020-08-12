package com.example.aimusicplayer

import android.media.MediaPlayer
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.File
@Parcelize
data class SongsModel(val mysong:ArrayList<File>,val songName:String,val position:Int):Parcelable{

}
