package com.example.neatshoe

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.IOException
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {

    class User(val name: String, val email: String, val address: String, val phone: String, val point: Int, val image: String)


    val IMAGE_CODE = 1

    lateinit var databaseReference: DatabaseReference
    lateinit var mBase: FirebaseDatabase
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var uid: String
    lateinit var profileImage: CircleImageView
    lateinit var imageUri: Uri

    lateinit var nameBox: LinearLayout
    lateinit var profileName: EditText
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

        user = FirebaseAuth.getInstance().currentUser!!
        uid = user.uid
        profileName = requireActivity().findViewById(R.id.profileName)
        profileImage = requireActivity().findViewById<View>(R.id.profileImage) as CircleImageView
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

        databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child("Users").child(uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Network ERROR. Please check your connection", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user_name: String = dataSnapshot.child("name").value.toString()
                var user_email: String = dataSnapshot.child("email").value.toString()
                var user_image: String = dataSnapshot.child("image").value.toString()
                var user_phone: String = dataSnapshot.child("phone").value.toString()
                var user_point: String = dataSnapshot.child("point").value.toString()
                var user_address: String = dataSnapshot.child("address").value.toString()

                profileName.setText(user_name)
                profileEmail.setText(user_email)
                profileAddress.setText(user_address)
                profilePoint.setText(user_point)
                profilePhone.setText(user_phone)
                try{
                    //if image is received then set it
                    Picasso.get().load(user_image).into(profileImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Name.text = user_name

            }
        })

        edit.setOnClickListener() {
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

        save.setOnClickListener() {
            save()
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE && data != null) {
            imageUri = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                profileImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }
    }

    private fun save() {

        storeData()
        Toast.makeText(activity, "Update Successfully", Toast.LENGTH_SHORT).show()
        profileImage.isEnabled = false
        profileEmail.isEnabled = false
        profileAddress.isEnabled = false
        profilePhone.isEnabled = false
        nameBox.isGone = true
        save.isGone = true
        edit.isGone = false


    }

    private fun storeData() {

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(imageUri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                saveUserToFirebaseDatabase(it.toString())
            }
        }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        val name = profileName.text.toString().trim()
        val email = profileEmail.text.toString().trim()
        val address = profileAddress.text.toString().trim()
        val phone = profilePhone.text.toString().trim()
        val point = profilePoint.text.toString().trim()

        val user = User(name, email, address, phone, point.toInt(), profileImageUrl)

        ref.setValue(user)
    }
}


