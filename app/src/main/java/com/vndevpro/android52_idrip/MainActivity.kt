package com.vndevpro.android52_idrip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vndevpro.android52_idrip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null)
        binding = ActivityMainBinding.bind(view)
        binding.lifecycleOwner = this

        setContentView(binding.root)
        val userViewModel = UserViewModel()

        userViewModel.getInfo()

        userViewModel.userModel.observe(this, Observer {
            binding.userModel = it
        })

        var click = HandlerClick(this)
        binding.clickHandler = click

        binding.tvFirstName.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
        }

    }

    fun click(view: View) {
        Log.d("TAG", "click: ")
    }

    class HandlerClick(val context: Context) {

        fun clickListener(view: View) {
            Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
        }

        fun clickListener(view: View, userModel: UserModel) {
            Toast.makeText(context, "click ${userModel.firstName}", Toast.LENGTH_SHORT).show()
        }
    }
}