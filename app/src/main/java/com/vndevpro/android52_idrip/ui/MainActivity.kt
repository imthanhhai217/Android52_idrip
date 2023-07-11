package com.vndevpro.android52_idrip.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databases.wishdb.WishListDatabase
import com.vndevpro.android52_idrip.databinding.ActivityMainBinding
import com.vndevpro.android52_idrip.repositories.HomeRepository
import com.vndevpro.android52_idrip.repositories.WishListRepository
import com.vndevpro.android52_idrip.ui.viewmodels.*
import com.vndevpro.android52_idrip.utils.Constants

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    lateinit var accountViewModel: AccountViewModel
    lateinit var categoriesViewModel: CategoriesViewModel

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
        initAccountViewModel()
        initCategoriesViewModel()
    }

    override fun onResume() {
        super.onResume()
        val openTab = intent.getStringExtra(Constants.OPEN_TAB_FROM_NOTIFICATION) ?: null
        if (openTab != null) {
            Log.d(TAG, "onResume: $openTab")
            openTab(openTab)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val openTab = intent?.getStringExtra(Constants.OPEN_TAB_FROM_NOTIFICATION) ?: null
        if (openTab != null) {
            Log.d(TAG, "onResume: $openTab")
            openTab(openTab)
        }
    }

    private fun openTab(openTab: String) {
        when (openTab) {
            "1" -> {
                binding.bottomNavigation.selectedItemId = R.id.wishFragment
            }
            "2" -> {
                binding.bottomNavigation.selectedItemId = R.id.categoriesFragment
            }
            "3" -> {
                binding.bottomNavigation.selectedItemId = R.id.accountFragment
            }
            "4" -> {
                binding.bottomNavigation.selectedItemId = R.id.cartFragment
            }
        }
    }

    private fun initCategoriesViewModel() {
        val categoriesViewModelFactory = CategoriesViewModelFactory(application)
        categoriesViewModel =
            ViewModelProvider(this, categoriesViewModelFactory)[CategoriesViewModel::class.java]
    }

    private fun initAccountViewModel() {
        val accountViewModelFactory = AccountViewModelFactory(application)
        accountViewModel =
            ViewModelProvider(this, accountViewModelFactory)[AccountViewModel::class.java]
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