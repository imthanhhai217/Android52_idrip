package com.vndevpro.android52_idrip.ui.fragements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.adapters.ListProductAdapter
import com.vndevpro.android52_idrip.api.BaseResponse
import com.vndevpro.android52_idrip.databinding.FragmentHomeBinding
import com.vndevpro.android52_idrip.models.ListProductResponse
import com.vndevpro.android52_idrip.models.Product
import com.vndevpro.android52_idrip.ui.MainActivity
import kotlin.streams.toList

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    lateinit var listProductAdapter: ListProductAdapter
    private val imageList = ArrayList<SlideModel>()

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
                    it.data?.let { response ->
                        Log.d(TAG, "onCreate: ${response.products.size}")
                        (activity as MainActivity).fakeModel = response.products[0]
                        updateData(response)
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

            fetchListHotDeals(listProduct)
        }

    }

    private fun fetchListHotDeals(listProduct: List<Product>) {
        val hotDeals =
            listProduct.stream().filter { product -> product.discountPercentage > 15 }.toList()
        val hotDealAdapter = ListProductAdapter(callback)
        binding.rvHotDeals.apply {
            adapter = hotDealAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        hotDealAdapter.differ.submitList(hotDeals)
    }

    private fun initView() {
        listProductAdapter = ListProductAdapter(callback)
        binding.rvListProduct.apply {
            adapter = listProductAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

        initDataSlider()
    }

    private fun initDataSlider() {
        imageList.add(
            SlideModel(
                "https://bit.ly/2YoJ77H", ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2", ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel("https://bit.ly/3fLJf72", ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
    }

    private val callback = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = listProductAdapter.differ.currentList[position]
            product.isWish = !product.isWish
            listProductAdapter.notifyItemChanged(position)
            if (product.isWish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {
            TODO("Not yet implemented")
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }
}