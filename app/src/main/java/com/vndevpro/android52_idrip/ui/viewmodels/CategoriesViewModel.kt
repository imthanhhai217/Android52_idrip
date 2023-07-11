package com.vndevpro.android52_idrip.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vndevpro.android52_idrip.models.Product

class CategoriesViewModel(private val myApplication: Application) :
    AndroidViewModel(myApplication) {
    private val TAG = "CategoriesViewModel"
    val database = Firebase.database

    init {
//        val product = Product()
        val productRef = database.getReference("products")
//        productRef.push().setValue(product)

        productRef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val model = snapshot.getValue(Product::class.java)
                Log.d(TAG, "onChildAdded: ${model.toString()}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val model = snapshot.getValue(Product::class.java)
                Log.d(TAG, "onChildChanged: ${model.toString()}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved: ")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    val model = i.getValue(Product::class.java)
                    Log.d(TAG, "onDataChange: ${model.toString()}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun sendMessage(message: String) {
        val myRef = database.getReference("history2")
//        myRef.setValue(message)
        myRef.push().setValue(message)
        val product = Product()
        val productRef = database.getReference("products")
        productRef.push().setValue(product)

        productRef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val model = snapshot.getValue(Product::class.java)
                Log.d(TAG, "onChildAdded: ${model.toString()}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val model = snapshot.getValue(Product::class.java)
                Log.d(TAG, "onChildChanged: ${model.toString()}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved: ")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val model = snapshot.getValue(Product::class.java)
                Log.d(TAG, "onDataChange: ${model.toString()}")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d(TAG, "onDataChange: ${snapshot.getValue(String::class.java)}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ")
            }

        })
    }
}