//package com.example.mylocationtrackoverfbdb
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationListener
//import android.location.LocationManager
//import android.os.Bundle
//import android.view.View
//import android.widget.EditText
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.FragmentActivity
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.firebase.database.*
//import java.util.*
//
//class MapsActivity : FragmentActivity(), OnMapReadyCallback {
//    private var mMap: GoogleMap? = null
//    private var databaseReference: DatabaseReference? = null
//    private var locationListener: LocationListener? = null
//    private var locationManager: LocationManager? = null
//    private val MIN_TIME: Long = 1000
//    private val MIN_DIST: Long = 5
//    private var editTextLatitude: EditText? = null
//    private var editTextLongitude: EditText? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment!!.getMapAsync(this)
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ),
//            PackageManager.PERMISSION_GRANTED
//        )
//        editTextLatitude = findViewById(R.id.editText)
//        editTextLongitude = findViewById(R.id.editText2)
//        databaseReference = FirebaseDatabase.getInstance().getReference("Location")
//        databaseReference!!.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                try {
//                    val databaseLatitudeString = dataSnapshot.child("latitude").value.toString()
//                        .substring(1, dataSnapshot.child("latitude").value.toString().length - 1)
//                    val databaseLongitudedeString = dataSnapshot.child("longitude").value.toString()
//                        .substring(1, dataSnapshot.child("longitude").value.toString().length - 1)
//                    val stringLat = databaseLatitudeString.split(", ").toTypedArray()
//                    Arrays.sort(stringLat)
//                    val latitude = stringLat[stringLat.size - 1].split("=").toTypedArray()[1]
//                    val stringLong = databaseLongitudedeString.split(", ").toTypedArray()
//                    Arrays.sort(stringLong)
//                    val longitude = stringLong[stringLong.size - 1].split("=").toTypedArray()[1]
//                    val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
//                    mMap!!.addMarker(
//                        MarkerOptions().position(latLng).title("$latitude , $longitude")
//                    )
//                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
////        // Add a marker in Sydney and move the camera
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        locationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                try {
//                    editTextLatitude!!.setText(java.lang.Double.toString(location.latitude))
//                    editTextLongitude!!.setText(java.lang.Double.toString(location.longitude))
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
//            override fun onProviderEnabled(s: String) {}
//            override fun onProviderDisabled(s: String) {}
//        }
//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return
//        }
//        try {
//            locationManager!!.requestLocationUpdates(
//                LocationManager.NETWORK_PROVIDER,
//                MIN_TIME,
//                MIN_DIST.toFloat(),
//                locationListener
//            )
//            locationManager!!.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                MIN_TIME,
//                MIN_DIST.toFloat(),
//                locationListener
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun updateButtonOnclick(view: View?) {
//        databaseReference!!.child("latitude").push().setValue(editTextLatitude!!.text.toString())
//        databaseReference!!.child("longitude").push().setValue(editTextLongitude!!.text.toString())
//    }
//}