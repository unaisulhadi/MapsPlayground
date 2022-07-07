package com.hadi.mapsplayground.misc

import android.content.Context
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.hadi.mapsplayground.R

class TypesAndStyle {


    fun setMapStyle(googleMap: GoogleMap,context: Context) {
        try{
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style))
            if(!success) {
                Log.d("MAPS", "setMapStyle: Failed to Add Style")
            }
        }catch (e : Exception) {
            Log.d("MAPS", e.toString())
        }
    }

    fun setMapType(item : MenuItem , mMap: GoogleMap) {
        when(item.itemId){

            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            R.id.hybrid_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }

            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }

            R.id.none_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
    }

}