package br.edu.infnet.rotagps_tp1_dr4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import java.io.File
import java.io.FileInputStream

class List : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"10:11.crd")
        val fis = FileInputStream(file)
        val bytes = fis.readBytes()
        fis.close()
        val tvText = this.findViewById<TextView>(R.id.tvText)
        tvText.text = String(bytes)

    }
}