package com.hadi.mapsplayground

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.hadi.mapsplayground.databinding.ActivityMapsBinding
import com.hadi.mapsplayground.misc.CameraAndViewPort
import com.hadi.mapsplayground.misc.CustomInfoAdapter
import com.hadi.mapsplayground.misc.MarkerUtils.vectorToBitmap
import com.hadi.mapsplayground.misc.TypesAndStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typesAndStyle by lazy { TypesAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }

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
        menuInflater.inflate(R.menu.map_types_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        typesAndStyle.setMapType(item, map)
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val losAngeles = LatLng(34.05373280386964, -118.2473114968821)
        val newYork = LatLng(40.71392607522911, -73.9915848140515)

        val laMarker = map.addMarker(MarkerOptions()
            .position(losAngeles)
            .title("Marker in LA")
            .snippet("This is content")
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.playstore)) // Works only if drawable is non vector
            .icon(vectorToBitmap(this,R.drawable.ic_map, Color.parseColor("#0000FF"))) // Use this if file is vector
            .alpha(0.5F)
            .flat(true) //Rotate marker along with map
            .zIndex(1f) //Used when multiple markers are there, to show current marker on top of all without overlapping
        )


        map.setOnMarkerClickListener(this)

        //Zoomed Camera from ( 1 to 20)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))

        // Enable/Disable UI settings for Map
        map.uiSettings.apply {
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

        map.setInfoWindowAdapter(CustomInfoAdapter(this))


    }

    override fun onMarkerClick(marker: Marker): Boolean {
        map.animateCamera(CameraUpdateFactory.zoomTo(17f),2000,null)
        marker.showInfoWindow()
        return true
    }


}