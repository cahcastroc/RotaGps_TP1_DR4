package br.edu.infnet.rotagps_tp1_dr4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.collections.List

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val tvNameFile =findViewById<TextView>(R.id.tvNameFile)
        val coordinates = intent.getStringExtra("Coordinates")
        val nameFile = intent.getStringExtra("Name")
        val tvCoordinates = findViewById<TextView>(R.id.tvCoordinates)
        tvNameFile.text = "Arquivo: $nameFile"
        tvCoordinates.text = coordinates

        val btReturn = findViewById<Button>(R.id.btReturn)

        btReturn.setOnClickListener {
            val intent = Intent(this, List::class.java)
            startActivity(intent)
        }




    }
}