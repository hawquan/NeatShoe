package com.example.neatshoe


import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_map.*
import java.util.*


class Map : AppCompatActivity() {
    var btn_PickLocation: Button? = null
    var btn_Save: Button? = null
    var tv_MyLatitude: TextView? = null
    var tv_MyLongitude: TextView? = null
    var tv_MyAddress: TextView? = null
    lateinit var databaseReference: DatabaseReference

    private val PLACE_PICKER_REQUEST = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        btn_PickLocation = findViewById<View>(R.id.BtnPickLocation) as Button
        btn_Save = findViewById<View>(R.id.BtSaveAddress) as Button
        tv_MyLatitude = findViewById<View>(R.id.MyLatitude) as TextView
        tv_MyLongitude = findViewById<View>(R.id.MyLongitude) as TextView
        tv_MyAddress = findViewById<View>(R.id.MyAddress) as TextView

        btn_PickLocation!!.setOnClickListener { //Disable Wifi
            openPlacePicker()
        }

        btn_Save!!.setOnClickListener(){
            UploadToDatabase()
        }

    }


    private fun openPlacePicker() {
        val builder = PlacePicker.IntentBuilder()
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)

        } catch (e: GooglePlayServicesRepairableException) {
            Log.d("Exception", e.message!!)
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.d("Exception", e.message!!)
            e.printStackTrace()
        }
    }

    private fun UploadToDatabase(){
        val uid = FirebaseAuth.getInstance().uid
        databaseReference = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        val address = tv_MyAddress?.text.toString().trim()
        val latitude = tv_MyLatitude?.text.toString().trim()
        val longitude = tv_MyLongitude?.text.toString().trim()

        databaseReference.child("address").setValue(address)
        databaseReference.child("latitude").setValue(latitude)
        databaseReference.child("longitude").setValue(longitude)

        Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PLACE_PICKER_REQUEST -> {
                    val place = PlacePicker.getPlace(this@Map, data)
                    val latitude = place.latLng.latitude
                    val longitude = place.latLng.longitude
                    MyLatitude!!.text = "$latitude"
                    MyLongitude!!.text = "$longitude"

                    val geocoder: Geocoder
                    val addresses: List<Address>
                    geocoder = Geocoder(this, Locale.getDefault())

                    addresses = geocoder.getFromLocation(latitude, longitude, 1)

                    val address = addresses[0].getAddressLine(0)
                    tv_MyAddress!!.text = address
                }
            }
        }
    }


}