package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import java.util.regex.Pattern


class Login : Fragment() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var ForgetPassword: TextView
    lateinit var btnLogin: Button
    lateinit var btnRegLogin: Button
    private lateinit var mAuth: FirebaseAuth
    lateinit var thisview: View



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        thisview=inflater.inflate(R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance()
        btnRegLogin = thisview.findViewById(R.id.btnRegLogin1)
        editEmail = thisview.findViewById(R.id.editEmail)
        editPassword = thisview.findViewById(R.id.editPassword)
        btnLogin = thisview.findViewById(R.id.btLogin)
        ForgetPassword =thisview.findViewById(R.id.forgetPassword)
        // Inflate the layout for this fragment
        btnRegLogin.setOnClickListener {view: View ->

            view.findNavController().navigate(R.id.action_login_to_register)
        }

        ForgetPassword.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_login_to_forgetpassword)

        }

        btnLogin.setOnClickListener() {

            val useremail = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()
            LoginWithAuth(useremail, password);
        }

        return thisview



    }


    private fun LoginWithAuth(email: String, password: String) {
        if (email.isEmpty() && password.isEmpty()) {
            editEmail.error = "Please enter email address"
            editPassword.error = "Please enter password"
            return
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {


                            Toast.makeText(activity, "Login Successfully", Toast.LENGTH_SHORT).show()
                            view!!.findNavController().navigate(R.id.action_login_to_home)
                        } else {
                            Toast.makeText(activity, "Invalid Username or password", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                })
    }

    private fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


}