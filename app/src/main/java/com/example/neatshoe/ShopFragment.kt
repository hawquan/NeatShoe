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
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.neatshoe.adapters.ShopListAdapter
import com.example.neatshoe.databinding.FragmentShopBinding
import com.example.neatshoe.models.Product
import com.example.neatshoe.viewmodels.ShopViewModel
import com.google.android.material.snackbar.Snackbar

class ShopFragment : Fragment(), ShopListAdapter.ShopInterface {

    private val TAG = "ShopFragment"
    var fragmentShopBinding: FragmentShopBinding? = null
    private var shopListAdapter: ShopListAdapter? = null
    private var shopViewModel: ShopViewModel? = null
    private var navController: NavController? = null

    fun ShopFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentShopBinding = FragmentShopBinding.inflate(inflater!!, container, false)
        return fragmentShopBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopListAdapter = ShopListAdapter(this)
        fragmentShopBinding?.shopRecyclerView?.setAdapter(shopListAdapter)
        fragmentShopBinding?.shopRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        fragmentShopBinding?.shopRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
        shopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        shopViewModel!!.products.observe(viewLifecycleOwner,
            { products -> shopListAdapter!!.submitList(products) })
        navController = Navigation.findNavController(view)
    }

    override fun addItem(product: Product) {
        val isAdded = shopViewModel!!.addItemToCart(product)
        if (isAdded) {
            Snackbar.make(
                requireView(),
                product.name.toString() + " added to cart.",
                Snackbar.LENGTH_LONG
            )
                .setAction(
                    "Checkout"
                ) { requireView().findNavController().navigate(R.id.action_shopFragment_to_cartFragment) }
                .show()
        } else {
            Snackbar.make(
                requireView(),
                "Already have the max quantity in cart.",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    override fun onItemClick(product: Product?) {
        shopViewModel!!.setProduct(product)
        requireView().findNavController().navigate(R.id.action_shopFragment_to_productDetailFragment)
    }
}