package com.hadi.mapsplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.hadi.mapsplayground.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_types_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

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
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // Default Camera Action
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //Zoomed Camera from ( 1 to 20)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))

        // Enable/Disable UI settings for Map
        mMap.uiSettings.apply {
            // Enable zoom control buttons
            isZoomControlsEnabled = true
            //Enable/Disable Zoom by gestures
            isZoomGesturesEnabled = true
            //Enable/Disable scrolling Map
            isScrollGesturesEnabled = true
            //Enable/Disable Rotating Map
            isRotateGesturesEnabled = true
            //Enable/Disable Map toolbar (Two buttons below screen when clicking marker)
            isMapToolbarEnabled = false
            //Enable/Disable Compass, (will show only if map is rotated)
            isCompassEnabled = true
        }
        /**
         * Add padding to right side of the screen,
         * buttons will also move w.r.to padding
         */
        mMap.setPadding(0,0,10,0)
        setMapStyle(mMap)
    }


    private fun setMapStyle(googleMap: GoogleMap) {
        try{
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style))
            if(!success) {
                Log.d("MAPS", "setMapStyle: Failed to Add Style")
            }
        }catch (e : Exception) {
            Log.d("MAPS", e.toString())
        }
    }
}