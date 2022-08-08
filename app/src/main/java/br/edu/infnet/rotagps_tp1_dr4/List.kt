package br.edu.infnet.rotagps_tp1_dr4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.ListView
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


        var files: Array<out File>? = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.listFiles()

        var teste = ArrayList<String>()

        if (files != null) {
            for(i in 0..files.size-1){
                teste.add(files.get(i).name.toString())
            }

        val listFiles = findViewById<ListView>(R.id.listFiles)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,teste)
        listFiles.adapter = adapter

        }
    }
}