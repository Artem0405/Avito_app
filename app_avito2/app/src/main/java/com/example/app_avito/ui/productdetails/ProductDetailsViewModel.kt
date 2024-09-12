package com.example.app_avito.ui.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_avito.data.model.Product
import com.example.app_avito.data.repository.ProductRepository
import com.example.app_avito.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productState = MutableStateFlow<Resource<Product>?>(null)
    val productState: StateFlow<Resource<Product>?> = _productState

    init {
        val productId = savedStateHandle.get<Int>("productId")
        if (productId != null) {
            getProductDetails(productId)
        }
    }

    private fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            _productState.value = Resource.Loading()
            productRepository.getProductById(productId).collect {
                _productState.value = it
            }
        }
    }
}