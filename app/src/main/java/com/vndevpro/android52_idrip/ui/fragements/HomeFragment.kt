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
import com.vndevpro.android52_idrip.api.BaseResponse
import com.vndevpro.android52_idrip.databinding.FragmentHomeBinding
import com.vndevpro.android52_idrip.models.ListProductResponse
import com.vndevpro.android52_idrip.ui.MainActivity

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    lateinit var listProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val homeViewModel = (activity as MainActivity).homeViewModel

        initView()

        homeViewModel.listProductResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResponse.Success -> {
                    hideLoading()
                    it.data?.let {
                        Log.d(TAG, "onCreate: ${it.products.size}")
                        (activity as MainActivity).fakeModel = it.products[0]
                        updateData(it)
                    }
                }

                is BaseResponse.Error -> {
                    hideLoading()
                    Log.d(TAG, "onCreate: ${it.message}")
                }

                is BaseResponse.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun updateData(response: ListProductResponse) {
        response.products?.let { listProduct ->
            listProductAdapter.updateData(listProduct)
        }
    }

    private fun initView() {
        listProductAdapter = ListProductAdapter()
        binding.rvListProduct.apply {
            adapter = listProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }
}