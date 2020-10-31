package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth


class forgetpassword : DialogFragment() {
    lateinit var btnCancel: Button
    lateinit var btnSend: Button
    lateinit var editEmail:EditText
    private lateinit var mAuth: FirebaseAuth



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView=inflater.inflate(R.layout.fragment_forgetpassword, container, false)
        mAuth = FirebaseAuth.getInstance()
        btnCancel= rootView.findViewById(R.id.btnForgetCancel)
        btnSend = rootView.findViewById(R.id.btnSend)
        editEmail =rootView.findViewById(R.id.editForgetEmail)
        btnSend.setOnClickListener {
            val useremail = editEmail.text.toString().trim()
            forgetPassword(useremail)
        }
        return rootView
    }
    private fun forgetPassword(userEmail: String){
        if(userEmail.isEmpty()){

            return
        }

        mAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "Email sent", Toast.LENGTH_SHORT).show()
                    }
                }

    }

}