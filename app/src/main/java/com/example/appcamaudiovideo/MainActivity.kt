package com.example.appcamaudiovideo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appcamaudiovideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idAudioBtn.setOnClickListener() {

            val intent1 = Intent(this, RegistrarAudio::class.java)
            startActivity(intent1)
        }


        binding.idCameraBtn.setOnClickListener(){

            val intent2=Intent(this, CamaraFotos::class.java)
            startActivity(intent2)
        }

        binding.idVideoBtn.setOnClickListener(){

            val intent3=Intent(this, ReproductorVideo::class.java)
            startActivity(intent3)
        }


    }

}