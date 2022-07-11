package com.hadi.mapsplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hadi.mapsplayground.databinding.ActivityMapsBinding
import com.hadi.mapsplayground.misc.CameraAndViewPort
import com.hadi.mapsplayground.misc.TypesAndStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener {

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

        val laMarker = map.addMarker(MarkerOptions().position(losAngeles).title("Marker in LA"))


        /************** MARKERS *************/
        /**
         *
         * Disable default info window show up in marker clicks
         *
         */
        laMarker?.tag = "Restaurant"
        map.setOnMarkerClickListener(this)

        /**
         * make marker draggable
         */
        laMarker?.isDraggable = true
        map.setOnMarkerDragListener(this)


        // Default Camera Action
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //Zoomed Camera from ( 1 to 20)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f))

        //Animate
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(losAngeles, 10f),4000,null)


        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles))

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
        /**
         * Add padding to right side of the screen,
         * buttons will also move w.r.to padding
         */
        map.setPadding(0, 0, 10, 0)
        typesAndStyle.setMapStyle(map, this)

        //Set min and Max zoom levels
        //map.setMinZoomPreference(15f)
        //map.setMaxZoomPreference(17f)


        /**
         * zoomBy() will add the zoom amount to existing zoom level;
         * Eg: if zoom level is 17f and zoom by is 3f,
         * then final zoom level will be 17+3 = 20f
         *
         */
        //lifecycleScope.launch {
        //    delay(4000L)
        //    map.moveCamera(CameraUpdateFactory.zoomBy(3f))
        //}

        /**
         * Move to new lat long
         */
//        lifecycleScope.launch {
//            delay(4000L)
//            map.moveCamera(CameraUpdateFactory.newLatLng(newYork))
//        }

        /**
         * Scroll through X and Y axis
         */
//        lifecycleScope.launch {
//            delay(4000)
//            map.moveCamera(CameraUpdateFactory.scrollBy(-200f,100f))
//        }

        lifecycleScope.launch {
            delay(4000)
            //map.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds,10))

            //Zoom to center of bound
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraAndViewPort.melbourneBounds.center,10f))

            //Limit scrolling with a bound
            //map.moveCamera(CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds,10))
            //map.setLatLngBoundsForCameraTarget(cameraAndViewPort.melbourneBounds)

            //Animate Movements
//            map.animateCamera(
//                CameraUpdateFactory.newLatLngBounds(cameraAndViewPort.melbourneBounds, 10),
//                2000,
//                null)


            //Zoom with Animation
            //map.animateCamera(CameraUpdateFactory.zoomBy(3f),2000,null)













            //Zoom with multiple properties like zoom, target, bearing, tilt
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.losAngeles),2000,object : GoogleMap.CancelableCallback {
                override fun onCancel() {
                    Toast.makeText(this@MapsActivity, "Cancelled", Toast.LENGTH_SHORT).show()
                }

                override fun onFinish() {
                    Toast.makeText(this@MapsActivity, "Finished", Toast.LENGTH_SHORT).show()
                }
            })

            map.setOnMapClickListener {
                //Do stuff
                Toast.makeText(this@MapsActivity, "Lat : ${it.latitude} \nLng : ${it.longitude}", Toast.LENGTH_SHORT).show()
            }

            map.setOnMapLongClickListener {
                //Do Stuff
                map.addMarker(MarkerOptions().position(it).title("Marker in Somewhere"))
            }
        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d("MARKER_PG", marker.tag as String)

        /**
         * returning true will disable showing marker info on click
         */
        return true
    }

    override fun onMarkerDrag(p0: Marker) {
        Log.d("MARKER_PG","DRAG")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        Log.d("MARKER_PG","END")
    }

    override fun onMarkerDragStart(p0: Marker) {
        Log.d("MARKER_PG","START")
    }


}