package com.hadi.mapsplayground.misc

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class CameraAndViewPort {

    val losAngeles : CameraPosition= CameraPosition.Builder()
        .target(LatLng(34.05373280386964, -118.2473114968821))
        .zoom(17f)
        .bearing(100f)
        .tilt(45f)
        .build()

    val melbourneBounds = LatLngBounds(
        LatLng(-38.32430529175657, 144.41727655906627), //SW
        LatLng(-37.494356776545416, 145.4856969287369), //NE

    )

}