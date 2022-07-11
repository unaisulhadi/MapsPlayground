package com.hadi.mapsplayground.misc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MarkerUtils {
    fun vectorToBitmap(context: Context, id: Int, color: Int) : BitmapDescriptor {
        val vectorDrawable : Drawable?= ResourcesCompat.getDrawable(context.resources,id,null)
        if(vectorDrawable == null) {
            Log.d("MapsActivity", "vectorToBitmap: Not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
        DrawableCompat.setTint(vectorDrawable,color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}