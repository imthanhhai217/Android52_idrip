package com.vndevpro.android52_idrip.ui.fragements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databinding.FragmentWishBinding
import com.vndevpro.android52_idrip.ui.MainActivity
import com.vndevpro.android52_idrip.ui.viewmodels.WishListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WishFragment : Fragment() {

    private val TAG = "WishFragment"

    lateinit var binding: FragmentWishBinding
    lateinit var wishListViewModel: WishListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        binding = FragmentWishBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishListViewModel = (activity as MainActivity).wishListViewModel

        wishListViewModel.upsertWish((activity as MainActivity).fakeModel)

        wishListViewModel.getAllWishList().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: "+it.size)
        })
    }

}