package com.example.neatshoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.neatshoe.databinding.FragmentOrderBinding
import com.example.neatshoe.viewmodels.ShopViewModel

class OrderFragment : Fragment() {

    var navController: NavController? = null
    var fragmentOrderBinding: FragmentOrderBinding? = null
    var shopViewModel: ShopViewModel? = null

    fun OrderFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater!!, container, false)
        return fragmentOrderBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        shopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        fragmentOrderBinding!!.continueShoppingButton.setOnClickListener {
            shopViewModel!!.resetCart()
            requireView().findNavController().navigate(R.id.action_orderFragment_to_shopFragment)
        }
    }
}