package br.edu.infnet.rotagps_tp1_dr4

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), LocationListener {

    val COARSE_REQUEST = 1234
    val FINE_REQUEST = 5678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btLocation = this.findViewById<Button>(R.id.btLocation)
        val btSave = findViewById<Button>(R.id.btSave)
        val btRead = findViewById<Button>(R.id.btRead)


        btLocation.setOnClickListener {

            drawLayout(getLocationByGps())

        }

        btSave.setOnClickListener {

            val text = "Latitude: ${getLocationByGps()?.latitude.toString()} ; Longitude:${getLocationByGps()?.longitude.toString()}  "

            val name = date()

            if(isExternalStorageWritable()){
                val file = File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "$name.crd")
                val fos = FileOutputStream(file)
                fos.write(text.toByteArray())
                fos.close()
                Toast.makeText(this,"Salvo com sucesso - Nome do arquivo: $name",Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(this,"Mídia externa não disponível",Toast.LENGTH_LONG).show()
            }



        }

        btRead.setOnClickListener {
            val intent = Intent(this, List::class.java)
            startActivity(intent)
        }

    }


    private fun getLocationByGps() : Location?{


        var location: Location? = null
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isServiceEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isServiceEnabled) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)


            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_REQUEST
                )
            }
        }
        return location

    }


    private fun drawLayout(location: Location?)  {
        if(location != null){
            val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
            val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
            val latitude = location.latitude.toString()
            val longitude = location.longitude.toString()

            tvLatitude.text = latitude
            tvLongitude.text = longitude


        }

    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

//        if (requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            getLocationByNetwork()
//        }

        if (requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationByGps()
        }

    }

    override fun onLocationChanged(p0: Location) {

    }
    private fun date(): String {
        val date = Calendar.getInstance().time

        val dateTimeFormat = SimpleDateFormat("dd_MM_yyyy_HH-mm-ss", Locale.getDefault())

        return dateTimeFormat.format(date)
    }
}