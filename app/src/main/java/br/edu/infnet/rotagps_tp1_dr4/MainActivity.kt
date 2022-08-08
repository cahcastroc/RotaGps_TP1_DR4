package br.edu.infnet.rotagps_tp1_dr4

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), LocationListener {

    val COARSE_REQUEST = 1234
    val FINE_REQUEST = 5678

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btLocation = this.findViewById<Button>(R.id.btLocation)
        val btSave = findViewById<Button>(R.id.btSave)


        btLocation.setOnClickListener {

            getLocationByGps()

        }

        btSave.setOnClickListener {

        }

    }

    private fun getLocationByGps() {
        var location: Location?
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

                if (location != null) {
                    drawLayout(location)
                }

            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_REQUEST
                )
            }
        }

    }


    private fun getLocationByNetwork() {

        var location: Location?
        val locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isServiceEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (isServiceEnabled) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                if (location != null) {
                    drawLayout(location)
                }

            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    COARSE_REQUEST
                )
            }
        }


    }

    private fun drawLayout(location: Location) {
        val tvLatitude = findViewById<TextView>(R.id.tvLatitude)
        val tvLongitude = findViewById<TextView>(R.id.tvLongitude)
        val latitude = location.latitude.toString()
        val longitude = location.longitude.toString()

        tvLatitude.text = latitude
        tvLongitude.text = longitude

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == COARSE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationByNetwork()
        }

        if (requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationByGps()
        }

    }

    override fun onLocationChanged(p0: Location) {

    }
}