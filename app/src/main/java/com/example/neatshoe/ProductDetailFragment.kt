package com.example.neatshoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.neatshoe.databinding.FragmentProductDetailBinding
import com.example.neatshoe.viewmodels.ShopViewModel

class ProductDetailFragment : Fragment() {

    var fragmentProductDetailBinding: FragmentProductDetailBinding? = null
    var shopViewModel: ShopViewModel? = null

    fun ProductDetailFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductDetailBinding =
            FragmentProductDetailBinding.inflate(inflater!!, container, false)
        return fragmentProductDetailBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        fragmentProductDetailBinding?.setShopViewModel(shopViewModel)
    }
}