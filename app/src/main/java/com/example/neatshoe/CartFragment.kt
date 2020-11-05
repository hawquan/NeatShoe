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
import com.example.neatshoe.adapters.CartListAdapter
import com.example.neatshoe.databinding.FragmentCartBinding
import com.example.neatshoe.models.CartItem
import com.example.neatshoe.viewmodels.ShopViewModel

class CartFragment : Fragment(), CartListAdapter.CartInterface {

    private val TAG = "CartFragment"
    var shopViewModel: ShopViewModel? = null
    var fragmentCartBinding: FragmentCartBinding? = null
    var navController: NavController? = null

    fun CartFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater!!, container, false)
        return fragmentCartBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val cartListAdapter = CartListAdapter(this)
        fragmentCartBinding!!.cartRecyclerView.adapter = cartListAdapter
        fragmentCartBinding!!.cartRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        shopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        shopViewModel!!.cart.observe(viewLifecycleOwner,
            { cartItems ->
                cartListAdapter.submitList(cartItems)
                fragmentCartBinding!!.placeOrderButton.isEnabled = cartItems.size > 0
            })
        shopViewModel!!.totalPrice.observe(viewLifecycleOwner,
            { aDouble -> fragmentCartBinding!!.orderTotalTextView.text = "Total: $ $aDouble" })
        fragmentCartBinding!!.placeOrderButton.setOnClickListener { requireView().findNavController().navigate(R.id.action_cartFragment_to_orderFragment) }
    }

    override fun deleteItem(cartItem: CartItem?) {
        shopViewModel!!.removeItemFromCart(cartItem)
    }

    override fun changeQuantity(cartItem: CartItem?, quantity: Int) {
        shopViewModel!!.changeQuantity(cartItem, quantity)
    }
}