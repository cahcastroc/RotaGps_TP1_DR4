package br.edu.infnet.rotagps_tp1_dr4

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.view.View
import android.widget.*
import androidx.core.view.get
import java.io.File
import java.io.FileInputStream
import kotlin.collections.List

class List : AppCompatActivity(), AdapterView.OnItemClickListener {

    val PICK_PDF_FILE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)



        if (isExternalStorageWritable()) {
            var files: Array<out File>? =
                getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.listFiles()

            var teste = ArrayList<String>()

            if (files != null) {
                for (i in 0..files.size - 1) {
                    teste.add(files.get(i).name.toString())
                }

                val listFiles = findViewById<ListView>(R.id.listFiles)
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, teste)
                listFiles.adapter = adapter
                listFiles.choiceMode = ListView.CHOICE_MODE_SINGLE
                listFiles.onItemClickListener = this

                Toast.makeText(this, "${isExternalStorageWritable()}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Mídia externa não disponível", Toast.LENGTH_LONG).show()

            }


        }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }




    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var items :String = p0?.getItemAtPosition(p2) as String



        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), items)
        val fis = FileInputStream(file)
        val bytes = fis.readBytes()
        fis.close()

        val intent = Intent(this, Details::class.java)
        intent.putExtra("Name",items)
        intent.putExtra("Coordinates",String(bytes))

        startActivity(intent)

        Toast.makeText(this, "oi ${String(bytes)}", Toast.LENGTH_LONG).show()


}}