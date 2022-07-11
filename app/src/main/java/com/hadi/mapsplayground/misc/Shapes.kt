package com.hadi.mapsplayground.misc

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.hadi.mapsplayground.R
import kotlinx.coroutines.delay

object Shapes {

    val losAngeles = LatLng(34.05373280386964, -118.2473114968821)
    val newYork = LatLng(40.71392607522911, -73.9915848140515)
    val madrid = LatLng(40.422067989338444, -3.7035171415112678)
    val panama = LatLng(9.092803671276164, -79.53469763577102)


    private val p0 = LatLng(34.482764414524596, -119.02875108736872)
    private val p1 = LatLng(34.569332676663585, -117.1373948871637)
    private val p2 = LatLng(33.61215333222147, -116.97889140145912)
    private val p3 = LatLng(33.616602733572925, -119.39740526198493)

    private val p00 = LatLng(34.32112591104362, -118.581735638921)
    private val p01 = LatLng(34.19306361276768, -117.46864936290582)
    private val p02 = LatLng(33.81066904468141, -117.29055555874339)
    private val p03 = LatLng(33.81806746160135, -118.60666877150375)

    suspend fun addPolyline(map: GoogleMap) {

        val pattern = listOf(Dot(), Gap(30f), Dash(50f), Gap(30f))
        val polyline = map.addPolyline(
            PolylineOptions().apply {
                add(losAngeles, newYork, madrid)
                width(20f)
                color(Color.BLUE)
                geodesic(true) // Curved polyline
                clickable(true)
                //pattern(pattern)
                jointType(JointType.ROUND) //Rounded intersections
                startCap(CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ic_power_start),100f))
                endCap(RoundCap())
            }
        )

        delay(4000)
        val newList = listOf<LatLng>(losAngeles, panama, madrid)

        polyline.points = newList
    }

    fun addPolygon(map: GoogleMap) {
        val polygon = map.addPolygon(
            PolygonOptions().apply {
                add(p0, p1, p2, p3)
                fillColor(R.color.teal_200)
                strokeColor(Color.BLUE)
                addHole(listOf(p00, p01, p02, p03))
            }
        )
    }

    fun addCircle(map: GoogleMap) {
        val circle = map.addCircle(
            CircleOptions().apply {
                center(losAngeles)
                radius(50000.0)
                fillColor(R.color.purple_200)
            }
        )
    }
}