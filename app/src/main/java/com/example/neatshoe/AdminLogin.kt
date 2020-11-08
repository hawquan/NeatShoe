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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern


class AdminLogin : Fragment() {

    class Account(
        val address: String="",
        val email: String = "",
        val image: String ="",
        val name: String = "",
        val password: String = "",
        val phone: String="",
        val point: String=""
    )

    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnLogin: Button
    lateinit var thisview: View
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisview =inflater.inflate(R.layout.fragment_admin_login, container, false)
        mAuth = FirebaseAuth.getInstance()
        editEmail = thisview.findViewById(R.id.editEmail)
        editPassword = thisview.findViewById(R.id.editPassword)
        btnLogin = thisview.findViewById(R.id.btAdminLogin)

        btnLogin.setOnClickListener {
            //val useremail = editEmail.text.toString().trim()
            //val password = editPassword.text.toString().trim()
            //LoginWithAuth(useremail, password);
            login()
        }


        return thisview
    }
    private fun login() {
        val username = editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            editEmail.error = "Please enter username"
            editPassword.error = "Please enter password"
            return
        }


        if (!isEmailValid(username)) {
            //Login with username and password
            val database = FirebaseDatabase.getInstance().reference

            database.child("Admin").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (h in dataSnapshot.children) {
                        val account = h.getValue(Account::class.java)
                        val username1 = account!!.name
                        val password1 = account.password
                        val email1 = account.email

                        if (username.equals(username1) && password.equals(password1)) {
                            mAuth.signInWithEmailAndPassword(email1, password1)


                            Toast.makeText(activity, "Login Successfully", Toast.LENGTH_SHORT)
                                .show()
                            view!!.findNavController().navigate(R.id.action_adminLogin_to_adminHomePage)

                        } else {
                            Toast.makeText(
                                activity,
                                "Invalid Username or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
        } else {
            //Login with email and password, firebase authentication
            LoginWithAuth(username, password)
        }

    }

    private fun LoginWithAuth(email: String, password: String) {
        /*if (email.isEmpty() || password.isEmpty()) {
            editEmail.error = "Please enter email address"
            editPassword.error = "Please enter password"
            return
        }*/

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {


                        Toast.makeText(activity, "Login Successfully", Toast.LENGTH_SHORT).show()
                        view!!.findNavController().navigate(R.id.action_adminLogin_to_adminHomePage)
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