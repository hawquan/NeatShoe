package com.example.neatshoe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class Logout : Fragment() {

    lateinit var navMenu: Menu
    lateinit var navigationView: NavigationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //logout
        FirebaseAuth.getInstance().signOut()

//        //get navigation drawer and the menu
//        navigationView = requireActivity().findViewById(R.id.nav_view)
//        navMenu = navigationView.menu
//
//
//        //disable logout and profile button
//        navMenu.findItem(R.id.btLogout).isVisible = false
//        //profileBtn.isEnabled = false
//        requireActivity().findViewById<Button>(R.id.btProfile).isEnabled = false
//
//        //enable login button and register button
//        navMenu.findItem(R.id.login).isVisible = true
//        navMenu.findItem(R.id.btnRegister).isVisible = true

//        requireFragmentManager().popBackStack()
        //back to login page
        requireView().findNavController().navigate(R.id.action_logout_to_login)

    }


}
