package com.hadi.mapsplayground.misc

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.hadi.mapsplayground.R

class Overlays {

    private val losAngeles = LatLng(34.05373280386964, -118.2473114968821)
    private val latLngBounds = LatLngBounds(
        LatLng(34.135995345444684, -118.53259019769047),
        LatLng(34.38002862528332, -117.99563341230885),
    )

    fun addGroundOverlay(map: GoogleMap) {
        val groundOverlay = map.addGroundOverlay(
            GroundOverlayOptions().apply {
                position(losAngeles,10000f,10000f)
                image(BitmapDescriptorFactory.fromResource(R.drawable.playstore))
            }
        )
    }

    fun addGroundOverlayBounds(map: GoogleMap) : GroundOverlay? {
        return map.addGroundOverlay(
            GroundOverlayOptions().apply {
                position(losAngeles,10000f,10000f)
                image(BitmapDescriptorFactory.fromResource(R.drawable.ic_green_love))
            }
        )
    }

}