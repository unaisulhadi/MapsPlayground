package com.hadi.mapsplayground

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.hadi.mapsplayground.databinding.ActivityMapsBinding
import com.hadi.mapsplayground.misc.CameraAndViewPort
import com.hadi.mapsplayground.misc.Overlays
import com.hadi.mapsplayground.misc.Shapes.addCircle
import com.hadi.mapsplayground.misc.Shapes.addPolygon
import com.hadi.mapsplayground.misc.Shapes.addPolyline
import com.hadi.mapsplayground.misc.TypesAndStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val typesAndStyle by lazy { TypesAndStyle() }
    private val cameraAndViewPort by lazy { CameraAndViewPort() }
    private val overlays by lazy { Overlays() }

    val losAngeles = LatLng(34.05373280386964, -118.2473114968821)
    val newYork = LatLng(40.71392607522911, -73.9915848140515)
    val madrid = LatLng(40.422067989338444, -3.7035171415112678)
    val panama = LatLng(9.092803671276164, -79.53469763577102)

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


        val laMarker = map.addMarker(MarkerOptions()
            .position(losAngeles)
            .title("Marker in LA")
            .snippet("This is content")
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))

        // Enable/Disable UI settings for Map
        map.uiSettings.apply {
            isZoomControlsEnabled = true
        }
        typesAndStyle.setMapStyle(map, this)
        //overlays.addGroundOverlay(map)
        val groundOverlay = overlays.addGroundOverlayBounds(map)
        lifecycleScope.launch {
            delay(4000L)
            //groundOverlay?.remove()
            groundOverlay?.transparency = 0.5f
        }

    }

}