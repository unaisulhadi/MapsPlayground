package com.hadi.mapsplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
            isZoomGesturesEnabled = false
            //Enable/Disable scrolling Map
            isScrollGesturesEnabled = false
            //Enable/Disable Rotating Map
            isRotateGesturesEnabled = true
            //Enable/Disable Map toolbar (Two buttons below screen when clicking marker)
            isMapToolbarEnabled = false
            //Enable/Disable Compass, (will show only if map is rotated)
            isCompassEnabled = true

        }
    }
}