//package com.example.neatshoe
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.Fragment
//import com.example.chcook.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.UserProfileChangeRequest
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//import de.hdodenhof.circleimageview.CircleImageView
//import java.io.IOException
//import java.util.*
//
//class Fragment_addStaff : Fragment(), View.OnClickListener {
//    var imageUri: Uri? = null
//    var fAuth: FirebaseAuth? = null
//    var fBase: FirebaseDatabase? = null
//    var reference: DatabaseReference? = null
//    private var profilePicStaff: CircleImageView? = null
//    private var register: Button? = null
//    private var clean: Button? = null
//    private var Email: EditText? = null
//    private var Name: EditText? = null
//    private var Pass: EditText? = null
//    private var CPass: EditText? = null
//    private var path: TextView? = null
//    private var ff: String? = null
//    private val lastURL: String? = null
//    private var valid = true
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view: View = inflater.inflate(R.layout.fragment_addstaff, container, false)
//        profilePicStaff = view.findViewById<View>(R.id.profilePic_staff) as CircleImageView
//        register = view.findViewById(R.id.btnRegister)
//        clean = view.findViewById(R.id.btnClear)
//        Email = view.findViewById(R.id.txtEmail)
//        Name = view.findViewById(R.id.txtName)
//        Pass = view.findViewById(R.id.txtPass)
//        path = view.findViewById(R.id.txtPath)
//        CPass = view.findViewById(R.id.txtCfmPass)
//        fBase = FirebaseDatabase.getInstance()
//        fAuth = FirebaseAuth.getInstance()
//        profilePicStaff!!.setOnClickListener(this)
//        register.setOnClickListener(this)
//        clean.setOnClickListener(this)
//        return view
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            imageUri = data!!.data
//            try {
//                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
//                profilePicStaff!!.setImageBitmap(bitmap)
//                //                Toast.makeText(getActivity(), imageUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.profilePic_staff -> {
//                val gall = Intent()
//                gall.type = "image/*"
//                gall.action = Intent.ACTION_GET_CONTENT
//                startActivityForResult(Intent.createChooser(gall, "Select Profile Pic"), PICK_IMAGE)
//            }
//            R.id.btnRegister -> {
//                val builderR = AlertDialog.Builder(activity!!)
//                builderR.setTitle("Confirm Registration")
//                builderR.setMessage("Are you sure want to confirm?")
//                builderR.setPositiveButton("Yes") { dialog, which ->
//                    check(Email)
//                    check(Name)
//                    check(Pass)
//                    check(CPass)
//                    val password = Pass!!.text.toString()
//                    if (valid) {
//                        if (password != CPass!!.text.toString()) {
//                            Pass!!.error = "Password not match!"
//                        } else {
//                            val sName = Name!!.text.toString()
//                            val sEmail = Email!!.text.toString()
//                            val sPass = Pass!!.text.toString()
//                            val sStatus = "Working"
//                            val IsAdmin = false
//                            fAuth!!.createUserWithEmailAndPassword(sEmail, sPass).addOnSuccessListener { authResult ->
//                                val id = authResult.user!!.uid
//                                //                                        Toast.makeText(getActivity(), "new staff account created", Toast.LENGTH_SHORT).show();
//                                val user = fAuth!!.currentUser
//                                reference = fBase!!.getReference("Staff").child(user!!.uid)
//                                //                                        Staff  st = new Staff(sEmail,sName,sPass,IsAdmin,sStatus);
////                                        updateUserInfo(sName,imageUri,fAuth.getCurrentUser());
//                                val mStorage = FirebaseStorage.getInstance().reference.child("staff_photos")
//                                val imageFilePath = mStorage.child(imageUri!!.lastPathSegment!!)
//                                imageFilePath.putFile(imageUri!!).addOnSuccessListener {
//                                    imageFilePath.downloadUrl.addOnSuccessListener { uri ->
//                                        val profileUpdate = UserProfileChangeRequest.Builder()
//                                                .setDisplayName(sName)
//                                                .setPhotoUri(uri)
//                                                .build()
//                                        ff = uri.toString()
//                                        user.updateProfile(profileUpdate)
//                                                .addOnCompleteListener { task ->
//                                                    //user info update success
//                                                    if (task.isSuccessful) {
//                                                        fAuth!!.currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
//                                                            if (task.isSuccessful) {
//                                                                val staffInfo: MutableMap<String, Any> = HashMap()
//                                                                staffInfo["StaffEmail"] = sEmail
//                                                                staffInfo["StaffName"] = sName
//                                                                //                                                                                        staffInfo.put("StaffPassword", sPass);
//                                                                staffInfo["StaffStatus"] = sStatus
//                                                                staffInfo["IsAdmin"] = IsAdmin
//                                                                staffInfo["ProfileImage"] = ff!!
//                                                                staffInfo["StaffId"] = id
//                                                                reference!!.setValue(staffInfo)
//                                                                Toast.makeText(v.context, "Register successfully ,please check email for verification", Toast.LENGTH_SHORT).show()
//                                                            } else {
//                                                                Toast.makeText(v.context, task.exception!!.message, Toast.LENGTH_SHORT).show()
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                    }
//                                }
//                                Email!!.setText("")
//                                Name!!.setText("")
//                                Pass!!.setText("")
//                                CPass!!.setText("")
//                            }.addOnFailureListener { Toast.makeText(activity, "Failed to register staff account", Toast.LENGTH_SHORT).show() }
//                        }
//                    }
//                }
//                builderR.setNegativeButton("No") { dialog, which -> }
//                val dialogR = builderR.create()
//                dialogR.show()
//            }
//            R.id.btnClear -> {
//                val builder = AlertDialog.Builder(activity!!)
//                builder.setTitle("Clear")
//                builder.setMessage("Are you sure want to clear?")
//                builder.setPositiveButton("Yes") { dialog, which ->
//                    Email!!.setText("")
//                    Name!!.setText("")
//                    Pass!!.setText("")
//                    CPass!!.setText("")
//                }
//                builder.setNegativeButton("No") { dialog, which -> }
//                val dialog = builder.create()
//                dialog.show()
//            }
//        }
//    }
//
//    private fun check(textField: EditText?): Boolean {
//        if (textField!!.text.toString().isEmpty()) {
//            textField.error = "Do not leave empty"
//            valid = false
//        } else {
//            valid = true
//        }
//        return valid
//    }
//
//    companion object {
//        private const val PICK_IMAGE = 1
//    }
//
//
