package com.example.neatshoe

import android.Manifest
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.fragment_profile.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {

    class User(val name: String, val email: String, val address: String, val phone: Int)

    val IMAGE_CODE = 1
    lateinit var databaseReference : DatabaseReference
    lateinit var user : FirebaseUser
    lateinit var uid : String
    lateinit var nameBox: LinearLayout
    lateinit var profileImage: CircleImageView
    lateinit var profileEmail: EditText
    lateinit var profileAddress: EditText
    lateinit var profilePhone: EditText
    lateinit var profilePoint: EditText
    lateinit var edit: Button
    lateinit var save: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

 //       user = FirebaseAuth.getInstance().currentUser!!
 //       uid = user.uid
            profileImage = requireActivity().findViewById(R.id.profileImage)
            profileEmail = requireActivity().findViewById(R.id.profileEmail)
            profileAddress = requireActivity().findViewById(R.id.profileAddress)
            profilePhone = requireActivity().findViewById(R.id.profilePhone)
            profilePoint = requireActivity().findViewById(R.id.profilePoint)
            nameBox = requireActivity().findViewById(R.id.nameBox)
            edit = requireActivity().findViewById(R.id.edit)
            save = requireActivity().findViewById(R.id.save)

         profileEmail.isEnabled = false
         profileImage.isEnabled = false
         profileAddress.isEnabled = false
         profilePhone.isEnabled = false
         profilePoint.isEnabled = false
         nameBox.isGone = true
         save.isGone = true

      // databaseReference = FirebaseDatabase.getInstance().reference

    /*   databaseReference.child("Users").child(uid).addValueEventListener(object : ValueEventListener{
           override fun onCancelled(databaseError : DatabaseError) {
               Toast.makeText(activity, "Network ERROR. Please check your connection", Toast.LENGTH_SHORT).show()
           }

           override fun onDataChange(dataSnapshot: DataSnapshot) {

               var user_name : String = dataSnapshot.child("name").value.toString()
               var user_email : String = dataSnapshot.child("email").value.toString()
               var user_username : String = dataSnapshot.child("username").value.toString()
               var user_point: String = dataSnapshot.child("point").value.toString()

               editName.setText(user_name)
               editEmail.setText(user_email)
               editUsername.setText(user_username)
               editPoint.setText(user_point)

           }
       })*/

       edit.setOnClickListener(){
           profileEmail.isEnabled = true
           profileAddress.isEnabled = true
           profilePhone.isEnabled = true
           profileImage.isEnabled = true
           nameBox.isGone = false
           save.isGone = false
           edit.isGone = true


       }

        profileImage.setOnClickListener() {
            pickImageFromGallery()
        }

       save.setOnClickListener(){
           save()
       }

   }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE){
            profileImage.setImageURI(data?.data)
        }
    }

   private fun save(){
/*     user = FirebaseAuth.getInstance().currentUser!!
     uid = user.uid
     val email = profileEmail.text.toString().trim()
     val address = profileAddress.text.toString().trim()
     val phone = profilePhone.text.toString().trim()
     val image = profilePhone.text.toString().trim()

     databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid)

     databaseReference.addValueEventListener(object : ValueEventListener {
         override fun onCancelled(databaseError: DatabaseError) {

         }

         override fun onDataChange(dataSnapshot: DataSnapshot) {
             val user = User(name, email, address, phone.toInt())
             databaseReference.setValue(user) */
             Toast.makeText(activity, "Update Successfully", Toast.LENGTH_SHORT).show()
             profileImage.isEnabled = false
             profileEmail.isEnabled = false
             profileAddress.isEnabled = false
             profilePhone.isEnabled = false
             nameBox.isGone = true
             save.isGone = true
             edit.isGone = false
//           }
//       })
 }

}