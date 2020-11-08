package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import android.util.Log
import android.view.*
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.NavigationUI
import com.example.neatshoe.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

private const val TAG : String = "Home"


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    private lateinit var mAuth : FirebaseAuth
    lateinit var navMenu: Menu
    lateinit var user: FirebaseUser
    lateinit var navigationView: NavigationView
    lateinit var headerview :View
    lateinit var uid: String

    //Check user logged in
    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {


        } else {
            requireView().findNavController().navigate(R.id.action_home_to_logout)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)
        mAuth = FirebaseAuth.getInstance()

        binding.btProfile.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_profile)
        }
        binding.Login.setOnClickListener { view : View ->
           // view.findNavController().navigate(R.id.action_home_to_login)
        }
        binding.btnRegister.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_register)
        }
        binding.btLogout.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_logout)
        }

        binding.btLocation2.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_map)
        }
        binding.btProduct.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_shopFragment)
        }
        binding.btAdminLogin.setOnClickListener { view :View ->
            //view.findNavController().navigate(R.id.action_home_to_adminLogin)
        }

        return binding.root
    }




    private fun functionForLoggedIn(){

        //get navigation drawer and the menu
        navigationView = requireActivity().findViewById(R.id.nav_view)
        navMenu = navigationView.menu


        //enable logout and profile button, redeem button
        navMenu.findItem(R.id.btLogout).isVisible = true
        requireActivity().findViewById<Button>(R.id.btProfile).isEnabled = true

    }


}