package com.vndevpro.android52_idrip.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vndevpro.android52_idrip.api.BaseResponse
import com.vndevpro.android52_idrip.models.ListProductResponse
import com.vndevpro.android52_idrip.repositories.HomeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(val homeRepository: HomeRepository, application: Application) :
    AndroidViewModel(application) {

    val listProductResult: MutableLiveData<BaseResponse<ListProductResponse>> = MutableLiveData()
    var listProductResponse: ListProductResponse? = null

    init {
        getListProduct()
    }

    private fun getListProduct() {
        viewModelScope.launch {
            listProductResult.value = BaseResponse.Loading()
            val result = homeRepository.getListProduct()
            listProductResult.postValue(handleListProduct(result))
        }
    }

    private fun handleListProduct(response: Response<ListProductResponse>): BaseResponse<ListProductResponse> {
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let { data ->
                listProductResponse = data
                return BaseResponse.Success(data)
            }
        }
        return BaseResponse.Error(null, "Load failed!")
    }
}