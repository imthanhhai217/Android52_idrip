package com.vndevpro.android52_idrip

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel:ViewModel() {
    var userModel = MutableLiveData<UserModel>()

    fun getInfo(){
        val model = UserModel("I'm", "Hải")
        userModel.value = model
    }

}