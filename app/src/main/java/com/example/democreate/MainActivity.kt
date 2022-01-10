package com.example.democreate

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.democreate.adapter.LocationListAdapter
import com.example.democreate.addLocationScreen.SearchLocationActivity
import com.example.democreate.database.DBHelper
import com.example.democreate.model.LocationModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rvLocationList: RecyclerView
    private lateinit var txtAddTripDetails: ImageView
    private var locationList = ArrayList<LocationModel>()

    var db: DBHelper? = null

    private lateinit var locationListAdapter: LocationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        int()
        setData()
        onClickView()

    }

    private fun int() {

        db = DBHelper(this)
        rvLocationList = findViewById(R.id.rvLocationView)
        txtAddTripDetails = findViewById(R.id.txtAddTripDetails)
    }

    private fun setData() {
        locationListView()
    }

    private fun onClickView() {
        txtAddTripDetails.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txtAddTripDetails -> {
                val intent = Intent(this, SearchLocationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Show List Of Location
    private fun locationListView() {

        locationList = db!!.getData()

        rvLocationList.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

        locationListAdapter =
            LocationListAdapter(this, locationList, object : LocationListAdapter.DeleteOnClick {

                // Remove Data From Database (Delete Click)
                override fun deleteClick(position: Int) {
                    val dialog = Dialog(this@MainActivity)
                    dialog.setContentView(R.layout.dilogbox_trip)
                    val yesBtnLogout: AppCompatButton = dialog.findViewById(R.id.yes_btn_logout)
                    val noBtnLogout: AppCompatButton = dialog.findViewById(R.id.no_btn_logout)

                    noBtnLogout.setOnClickListener {
                        dialog.dismiss()
                    }
                    yesBtnLogout.setOnClickListener {
                        db!!.deleteData(locationList[position].id)
                        locationListView()
                        dialog.dismiss()
                    }
                    dialog.create()
                    dialog.show()
                }
            }, object : LocationListAdapter.EditOnClick {
                override fun editClick(position: Int) {

                    var model = locationList[position]
                    val mainIntent = Intent(this@MainActivity, SearchLocationActivity::class.java)
                    mainIntent.putExtra("model", model.toString())
                    mainIntent.putExtra("type", "edit")
                    startActivity(mainIntent)
                }
            })
        rvLocationList.adapter = locationListAdapter

    }

    override fun onResume() {
        super.onResume()
        locationListView()
    }


}