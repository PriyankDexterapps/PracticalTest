package com.example.democreate.mapScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.democreate.R
import com.example.democreate.database.DBHelper
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.OnMapReadyCallback


class MapScreenActivity : AppCompatActivity() , OnMapReadyCallback {


    private var db: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_screen)
        db = DBHelper(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        val positions =0
        val lat = db!!.getLetLong()?.get(positions)?.lat
        val long = db!!.getLetLong()?.get(positions)?.long
        val sydney = LatLng(lat!! ,long!!)
        p0.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Location")
        )
        p0.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}