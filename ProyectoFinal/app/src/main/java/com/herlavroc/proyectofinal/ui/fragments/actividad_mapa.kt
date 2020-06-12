package com.herlavroc.proyectofinal.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.session.Session
import java.util.*


class actividad_mapa :  Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actividad_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        var codificar :Geocoder = Geocoder(activity, Locale.getDefault())
        val zoom = 16f
        val centerMap = LatLng(codificar.getFromLocationName(Session.actividad.descripcion,1).get(0).latitude, codificar.getFromLocationName(Session.actividad.descripcion,1).get(0).longitude)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))
        val centerMark = LatLng(codificar.getFromLocationName(Session.actividad.descripcion,1).get(0).latitude, codificar.getFromLocationName(Session.actividad.descripcion,1).get(0).longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(centerMark)
        markerOptions.title(Session.actividad.nombre)

        val bitmapDraw = context?.applicationContext?.let { ContextCompat.getDrawable(it, R.drawable.r_icon) } as BitmapDrawable
        val smallMarker = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 150, 150, false)

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))

        googleMap?.addMarker(markerOptions)
        googleMap?.setOnMarkerClickListener(this)

        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.custommap))

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        activity!!.onBackPressed();
        return true
    }


}
