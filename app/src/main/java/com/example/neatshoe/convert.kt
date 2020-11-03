//package com.students.ameer.placepikerapp
//
//import android.R
//import android.content.Intent
//import android.net.wifi.WifiManager
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException
//import com.google.android.gms.common.GooglePlayServicesRepairableException
//import com.google.android.gms.location.places.ui.PlacePicker
//
//class MainActivity : AppCompatActivity() {
//    var btn_PickLocation: Button? = null
//    var tv_MyLocation: TextView? = null
//    var wifiManager: WifiManager? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        btn_PickLocation = findViewById<View>(R.id.BtnPickLocation) as Button
//        tv_MyLocation = findViewById<View>(R.id.MyLocation) as TextView
//        wifiManager = this.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        btn_PickLocation!!.setOnClickListener { //Disable Wifi
//            wifiManager!!.isWifiEnabled = false
//            openPlacePicker()
//        }
//    }
//
//    private fun openPlacePicker() {
//        val builder = PlacePicker.IntentBuilder()
//        try {
//            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
//
//            //Enable Wifi
//            wifiManager!!.isWifiEnabled = true
//        } catch (e: GooglePlayServicesRepairableException) {
//            Log.d("Exception", e.message!!)
//            e.printStackTrace()
//        } catch (e: GooglePlayServicesNotAvailableException) {
//            Log.d("Exception", e.message!!)
//            e.printStackTrace()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK) {
//            when (requestCode) {
//                PLACE_PICKER_REQUEST -> {
//                    val place = PlacePicker.getPlace(this@Map, data)
//                    val latitude = place.latLng.latitude
//                    val longitude = place.latLng.longitude
//                    val PlaceLatLng = "$latitude , $longitude"
//                    tv_MyLocation!!.text = PlaceLatLng
//                }
//            }
//        }
//    }
//
//    companion object {
//        private const val PLACE_PICKER_REQUEST = 999
//    }
//}