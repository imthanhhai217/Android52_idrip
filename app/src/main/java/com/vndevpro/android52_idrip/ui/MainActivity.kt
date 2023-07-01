package com.vndevpro.android52_idrip.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databases.wishdb.WishListDatabase
import com.vndevpro.android52_idrip.databinding.ActivityMainBinding
import com.vndevpro.android52_idrip.models.Product
import com.vndevpro.android52_idrip.repositories.HomeRepository
import com.vndevpro.android52_idrip.repositories.WishListRepository
import com.vndevpro.android52_idrip.ui.viewmodels.HomeViewModel
import com.vndevpro.android52_idrip.ui.viewmodels.HomeViewModelFactory
import com.vndevpro.android52_idrip.ui.viewmodels.WishListViewModel
import com.vndevpro.android52_idrip.ui.viewmodels.WishListViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null)
        binding = ActivityMainBinding.bind(view)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)

        initHomeViewModel()
        initWishListViewModel()
    }

    private fun initWishListViewModel() {
        val wishListDatabase = WishListDatabase(this)
        val wishListRepository = WishListRepository(wishListDatabase)
        val wishListViewModelFactory = WishListViewModelFactory(wishListRepository, application)
        wishListViewModel =
            ViewModelProvider(this, wishListViewModelFactory)[WishListViewModel::class.java]
    }

    private fun initHomeViewModel() {
        val homeRepository = HomeRepository()
        val homeViewModelFactory = HomeViewModelFactory(homeRepository, application = application)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }


}