package com.vndevpro.android52_idrip.ui.fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vndevpro.android52_idrip.R
import com.vndevpro.android52_idrip.databinding.FragmentCategoriesBinding
import com.vndevpro.android52_idrip.ui.MainActivity
import com.vndevpro.android52_idrip.ui.viewmodels.CategoriesViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesViewModel: CategoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        binding = FragmentCategoriesBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesViewModel = (activity as MainActivity).categoriesViewModel

        binding.btnSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            categoriesViewModel.sendMessage(message)
        }
    }
}