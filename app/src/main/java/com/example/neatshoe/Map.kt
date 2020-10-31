package com.example.neatshoe

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.lang.IndexOutOfBoundsException
import java.util.*

class Map : AppCompatActivity(), OnMapReadyCallback, LocationListener,
GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener{
    private var mMap: GoogleMap? = null
    lateinit var mapView: MapView
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    private val DEFAULT_ZOOM = 15f
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var tvCurrentAddress: TextView

    override fun onMapReady(googleMap: GoogleMap) {
        mapView.onResume()
        mMap = googleMap

        askPermissionLocation()

        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        mMap!!.setMyLocationEnabled(true)
        mMap!!.setOnCameraMoveListener(this)
        mMap!!.setOnCameraMoveStartedListener(this)
        mMap!!.setOnCameraIdleListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById<MapView>(R.id.map1)

        tvCurrentAddress = findViewById<TextView>(R.id.tvAddress)
        askPermissionLocation()

        var mapViewBundle: Bundle? = null
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }

    public override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)

        askPermissionLocation()
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if(mapViewBundle == null){
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView.onSaveInstanceState(mapViewBundle)
    }

    private fun askPermissionLocation(){
        askPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ){
            getCurrentLocation()
        }.onDeclined { e ->
            if(e.hasDenied()) {
                e.denied.forEach {
                }

                AlertDialog.Builder(this)
                    .setMessage("Permission Denied, Unable to Continue")
                    .setPositiveButton("yes"){ _, _ ->
                        e.askAgain()
                    } // ask again
                    .setNegativeButton("no") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()){
                //the list of forever denied permission, user has check 'never ask again'
                e.foreverDenied.forEach{

                }
                //User have to do it manually by going to setting
                e.goToSettings()
            }
        }
    }
    private fun getCurrentLocation(){
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@Map)

        try{
            @SuppressLint("MissingPermission")
            val location =
                fusedLocationProviderClient!!.getLastLocation()

            location.addOnCompleteListener(object : OnCompleteListener<Location> {
                override fun onComplete(log: Task<Location>){
                    if(log.isSuccessful){
                        val currentLocation = log.result as Location?
                        if(currentLocation != null){
                            moveCamera(
                                LatLng(currentLocation.latitude, currentLocation.longitude),
                                DEFAULT_ZOOM
                            )
                        }
                    } else {
                        askPermissionLocation()
                    }
                }
            })
        } catch (se: Exception){
            Log.e("TAG", "Security Exception")
        }


    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try{
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        } catch (e: IOException){
            e.printStackTrace()
        }

        setAddress(addresses!![0])
    }

    private fun setAddress(addresses: Address){
        if( addresses != null){

            if(addresses.getAddressLine(0) != null){
                tvCurrentAddress!!.setText(addresses.getAddressLine(0))
            }
            if(addresses.getAddressLine(1) != null){
                tvCurrentAddress!!.setText(
                    tvCurrentAddress.getText().toString() + addresses.getAddressLine(1)
                )
            }
        }
    }

    override fun onCameraMove() {

    }

    override fun onCameraMoveStarted(p0: Int) {

    }

    override fun onCameraIdle() {
        var addresses: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try{

            addresses = geocoder.getFromLocation( mMap!!.getCameraPosition().target.latitude,
                mMap!!.getCameraPosition().target.longitude, 1)

            setAddress(addresses!![0])
        } catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

}