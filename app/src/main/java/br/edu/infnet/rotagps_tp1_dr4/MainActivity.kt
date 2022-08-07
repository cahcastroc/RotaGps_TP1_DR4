package br.edu.infnet.rotagps_tp1_dr4

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), LocationListener {

    val COARSE_REQUEST = 1234
    val FINE_REQUEST = 5678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btLocation = this.findViewById<Button>(R.id.btLocation)


        btLocation.setOnClickListener {

            getLocationByGps()
            getLocationByNetwork()

        }

    }

    private fun getLocationByGps() {
        var location: Location? = null
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isServiceEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isServiceEnabled){
            if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
                val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
                if (location != null) {
                    tvLatitude.text = location.latitude.toString()
                    tvLongitude.text = location.longitude.toString()
                } else {
                    Toast.makeText(this,"Net não ativa",Toast.LENGTH_LONG).show()
                }

            } else {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),FINE_REQUEST)
            }
        }

    }


    private fun getLocationByNetwork() {

        var location: Location? = null
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isServiceEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if(isServiceEnabled){
            if(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
                val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
                if (location != null) {
                    tvLatitude.text = location.latitude.toString()
                    tvLongitude.text = location.longitude.toString()
                } else {
                    Toast.makeText(this,"Net não ativa",Toast.LENGTH_LONG).show()
                }

            } else {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),COARSE_REQUEST)
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getLocationByNetwork()
        }

        if(requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getLocationByGps()
        }

    }

    override fun onLocationChanged(p0: Location) {

    }
}