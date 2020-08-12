package com.example.aimusicplayer
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.karumi.dexter.DexterBuilder
import java.util.*
import android.R.animator
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {
    lateinit var parentRelativeLayout: RelativeLayout
    lateinit var speechRecognizer: SpeechRecognizer
    lateinit var speechRecognizerIntent: Intent
    var keeper=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkVoiceCommandPermission()

        parentRelativeLayout=findViewById(R.id.parentRelativeLayout)
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this@MainActivity)
        speechRecognizerIntent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())





        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    }

                override fun onRmsChanged(rmsdB: Float) {
                   // onRmsChanged(rmsdB)
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                 }

                override fun onPartialResults(partialResults: Bundle?) {
                    }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    }

                override fun onBeginningOfSpeech() {
                    }

                override fun onEndOfSpeech() {
                    }

                override fun onError(error: Int) {
                   }

                override fun onResults(results: Bundle?) {
                    val matchFounder=results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                    if(matchFounder!=null){
                        keeper=matchFounder.get(0)
                        Toast.makeText(this@MainActivity,"results: $keeper",Toast.LENGTH_LONG).show()
                    }
                }

            }
        )

        parentRelativeLayout.setOnTouchListener(View.OnTouchListener { v, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN ->{
                    speechRecognizer.startListening(speechRecognizerIntent)
                    keeper=""

                }
                MotionEvent.ACTION_UP ->{
                    speechRecognizer.stopListening()
                }
            }
            return@OnTouchListener false
        })


    }
    fun checkVoiceCommandPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED)){
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+packageName))
                startActivity(intent)
                finish()
            }
        }
    }


}
