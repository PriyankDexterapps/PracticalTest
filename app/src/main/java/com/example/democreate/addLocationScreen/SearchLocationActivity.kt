package com.example.democreate.addLocationScreen

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.democreate.R
import com.example.democreate.database.DBHelper
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.gms.maps.model.LatLng

class SearchLocationActivity : AppCompatActivity() {

    private var apiKey: String = "AIzaSyAavypuxfrzJal2XRtvTb4j9xy_Y4r0lfs"

    private val requestCode = 1
    private var db: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)

        int()
        setData()
    }

    private fun int(){
        db = DBHelper(this)
    }

    private fun setData(){
        openSearchBar()
    }


    private fun openSearchBar(){
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        val fields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME , Place.Field.LAT_LNG)
        Places.createClient(this)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestCode) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        val destinationLatLng : LatLng = place.latLng!!

                        val lat = destinationLatLng.latitude
                        val long = destinationLatLng.longitude
                       Log.i(TAG, "Place: ${place.name}," + lat )

                        db!!.insertDataLocation(place.name , lat ,long )
                        finish()

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        status.statusMessage?.let { it1 -> Log.i(TAG, it1) }
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}