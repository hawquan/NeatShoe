package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*


class AdminHomePage : Fragment() {

    lateinit var databaseReference: DatabaseReference
    lateinit var thisview:View
    lateinit var user: FirebaseUser
    lateinit var uid: String
    lateinit var weltxt : TextView
    lateinit var btLogout : Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisview= inflater.inflate(R.layout.fragment_admin_home_page, container, false)
        user = FirebaseAuth.getInstance().currentUser!!
        uid = user.uid
        weltxt = thisview.findViewById(R.id.textView4)
        btLogout = thisview.findViewById(R.id.btAdminlogout)

        databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child("Admin").child(uid).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    activity,
                    "Network ERROR. Please check your connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user_name: String = dataSnapshot.child("name").value.toString()

                weltxt.setText("Welcome :"+user_name)



            }

        })

        btLogout.setOnClickListener { view : View ->
            FirebaseAuth.getInstance().signOut()
            view.findNavController().navigate(R.id.action_adminHomePage_to_selectRole)
            Toast.makeText(
                activity,
                "Log out successfully ",
                Toast.LENGTH_SHORT
            ).show()
        }


        return thisview
    }



}