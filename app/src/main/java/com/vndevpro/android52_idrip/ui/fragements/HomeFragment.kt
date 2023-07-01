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
import com.vndevpro.android52_idrip.utils.Constants
import java.util.function.Consumer
import kotlin.streams.toList

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    lateinit var listProductAdapter: ListProductAdapter
    private val imageList = ArrayList<SlideModel>()
    private var wishListData: List<Product>? = null
    private var listData: List<Product>? = null

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
                        listData = it.data.products
                        Log.d(TAG, "onCreate: ${response.products.size}")
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

        (activity as MainActivity).wishListViewModel.getAllWishList().observe(viewLifecycleOwner) {
            wishListData = it
            listData?.let { it1 -> fetchListHotDeals(it1) }
            listData?.let { it1 -> fetchListMostPopular(it1) }
        };
    }

    private fun updateData(response: ListProductResponse) {
        response.products?.let { listProduct ->
//            listProductAdapter.updateData(listProduct)

            fetchListHotDeals(listProduct)
            fetchListMostPopular(listProduct)
        }

    }

    lateinit var hotDealAdapter: ListProductAdapter
    private fun fetchListHotDeals(listProduct: List<Product>) {
        val hotDeals =
            listProduct.stream().filter { product -> product.discountPercentage > 15 }.toList()
        hotDealAdapter = ListProductAdapter(callbackHostDeals)
        binding.rvHotDeals.apply {
            adapter = hotDealAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

//        reset wish
        hotDeals.forEach(Consumer {
            it.isWish = false
        })

        wishListData?.forEach(Consumer { wishProduct ->
            val index = hotDeals.indexOfFirst { wishProduct.id == it.id }
            if (index >= 0) {
                hotDeals[index].isWish = true
            }
        })

        hotDealAdapter.differ.submitList(hotDeals)
    }

    lateinit var mostPopularAdapter: ListProductAdapter
    private fun fetchListMostPopular(listProduct: List<Product>) {
        val mostPopular = listProduct.stream().filter { product -> product.rating > 4.8 }.toList()
        mostPopularAdapter = ListProductAdapter(callbackHostDeals)
        binding.rvMostPopular.apply {
            adapter = mostPopularAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }


//        reset wish
        mostPopular.forEach(Consumer {
            it.isWish = false
        })

        wishListData?.forEach(Consumer { wishProduct ->
            val index = mostPopular.indexOfFirst { wishProduct.id == it.id }
            if (index >= 0) {
                mostPopular[index].isWish = true
            }
        })
        mostPopularAdapter.differ.submitList(mostPopular)
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
        if (imageList.size < Constants.SLIDER_SIZE) {
            imageList.clear()

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
        }
        binding.imageSlider.apply {
            setImageList(imageList)
            setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        }
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

    private val callbackHostDeals = object : ListProductAdapter.IClickListener {
        override fun changeWishStateListener(position: Int) {
            val product = hotDealAdapter.differ.currentList[position]
            product.isWish = !product.isWish
            hotDealAdapter.notifyItemChanged(position)
            if (product.isWish) {
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            } else {
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsProductListener(position: Int) {
        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }
}