package com.hadi.mapsplayground.misc

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.hadi.mapsplayground.R

class CustomInfoAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private val contentView = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window,null)


    override fun getInfoContents(marker: Marker): View? {
        renderViews(marker,contentView)
        return contentView
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderViews(marker,contentView)
        return contentView
    }

    fun renderViews(marker: Marker?,contentView: View) {
        val title = marker?.title
        val desc = marker?.snippet

        val titleTv = contentView.findViewById<TextView>(R.id.titleTextView)
        val descTv = contentView.findViewById<TextView>(R.id.descriptionTextView)

        titleTv.text = title
        descTv.text = desc
    }

}