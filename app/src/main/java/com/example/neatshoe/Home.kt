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
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.neatshoe.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
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
    lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)

        binding.btProfile.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_profile)
        }
        binding.btnLogin.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_login2)
        }
        binding.btnRegister.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_home_to_register)
        }
        return binding.root
    }





}