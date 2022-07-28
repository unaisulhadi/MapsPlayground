package com.hadi.mapsplayground

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.hadi.mapsplayground.databinding.ActivityMapsBinding
import com.hadi.mapsplayground.misc.CameraAndViewPort
import com.hadi.mapsplayground.misc.MyItem
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

    val losAngeles = LatLng(34.05373280386964, -118.2473114968821)
    val newYork = LatLng(40.71392607522911, -73.9915848140515)
    val madrid = LatLng(40.422067989338444, -3.7035171415112678)
    val panama = LatLng(9.092803671276164, -79.53469763577102)

    private val locationList = listOf(
        LatLng(33.987459169366396, -117.43514666922263),
        LatLng(34.02844168496524, -116.80892595820784),
        LatLng(33.987459169366396, -116.01791032324176),
        LatLng(33.78681578842077, -115.11153826995461),
        LatLng(33.850708044143765, -114.2546046745436),
        LatLng(33.58112491982409, -112.71651872998432),
        LatLng(33.51245201506937, -110.99166521283668),
        LatLng(33.549084352008315, -110.1621974436902),
        LatLng(33.484967570542835, -108.95919451960859),
        LatLng(32.50364494635834, -106.82235368711534),
    )

    private lateinit var clusterManager: ClusterManager<MyItem>

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
        addHeatMap()
    }

    private fun addHeatMap() {

        val colors = intArrayOf(
            Color.rgb(0, 128, 255),
            Color.rgb(204 , 0, 204),
            Color.rgb(255 ,255,51),
        )

        val startPoints = floatArrayOf(
            0.2f, 0.5f , 0.8f
        )

        val gradient = Gradient(colors, startPoints)

        val provider = HeatmapTileProvider.Builder()
            .data(locationList)
            .gradient(gradient)
            .opacity(0.3)
             .radius(50) //10 to 50  ; default 20
            .build()

        val overlays = map.addTileOverlay(TileOverlayOptions().tileProvider(provider))

        lifecycleScope.launch {
            delay(5000)
            overlays!!.clearTileCache()
            //provider.setRadius(50)
            overlays!!.remove()
        }
    }


}