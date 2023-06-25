package com.vndevpro.android52_idrip.ui.fragements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.adapters.ListProductAdapter
import com.vndevpro.android52_idrip.databinding.FragmentWishBinding
import com.vndevpro.android52_idrip.models.Product
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
    lateinit var mProductAdapter: ListProductAdapter

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

        initView()
        wishListViewModel.getAllWishList().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: " + it.size)
            it?.let {
                bindData(it)
            }
        })
    }

    private fun bindData(it: List<Product>) {
        mProductAdapter.updateData(it)
    }

    private fun initView() {
        mProductAdapter = ListProductAdapter(callback)
        binding.rvWishList.apply {
            adapter = mProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private val callback = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = mProductAdapter.differ.currentList[position]
            if (product.isWish) {
                wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {
        }
    }

}