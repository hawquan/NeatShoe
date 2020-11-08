package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController


class SelectRole : Fragment() {
    lateinit var btUser: Button
    lateinit var btAdmin: Button
    lateinit var thisview : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisview= inflater.inflate(R.layout.fragment_select_role, container, false)
        btUser= thisview.findViewById(R.id.btUser)
        btAdmin= thisview.findViewById(R.id.btAdmin)

        btUser.setOnClickListener { view: View ->
          view.findNavController().navigate(R.id.action_selectRole_to_login)

        }

        btAdmin.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_selectRole_to_adminLogin)

        }



        return thisview
    }


}