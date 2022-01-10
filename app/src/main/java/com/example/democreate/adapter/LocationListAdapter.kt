package com.example.democreate.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.democreate.R
import com.example.democreate.mapScreen.MapScreenActivity
import com.example.democreate.model.LocationModel

class LocationListAdapter(var mContext : Context ,private val locationList : List<LocationModel> ,var deleteRv: DeleteOnClick,var editRv : EditOnClick) : RecyclerView.Adapter<LocationListAdapter.LocationViewModel>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewModel {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_location_history,
                parent,
                false
            )
        return LocationViewModel(view)
    }

    override fun onBindViewHolder(holder: LocationViewModel, position: Int) {

        var list = locationList[position]

        holder.txtLocationName.text = list.location_name

        holder.imgDelete.setOnClickListener {
            deleteRv.deleteClick(position)
        }

        holder.imgEdit.setOnClickListener {
            editRv.editClick(position)
        }

        holder.itemView.setOnClickListener {

            var intent = Intent(mContext , MapScreenActivity::class.java)

            mContext.startActivity(intent)
        }


    }

    override fun getItemCount() = locationList.size

    class LocationViewModel (itemView : View) : RecyclerView.ViewHolder(itemView){

        var txtLocationName : TextView = itemView.findViewById(R.id.txtLocationName)
        var imgDelete : ImageView = itemView.findViewById(R.id.imgDelete)
        var imgEdit : ImageView = itemView.findViewById(R.id.imgEdit)
    }

    interface DeleteOnClick {
        fun deleteClick(position: Int)
    }
    interface EditOnClick {
        fun editClick(position: Int)
    }
}
