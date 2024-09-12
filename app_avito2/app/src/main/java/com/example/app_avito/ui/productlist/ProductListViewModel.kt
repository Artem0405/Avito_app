package com.example.app_avito.ui.productlist

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
class ProductListViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _productsState = MutableStateFlow<Resource<List<Product>>?>(null)
    val productsState: StateFlow<Resource<List<Product>>?> = _productsState

    init {
        getProducts()
    }

    fun getProducts(category: String? = null, sort: String? = null) {
        viewModelScope.launch {
            _productsState.value = Resource.Loading()
            productRepository.getProducts(category, sort).collect {
                _productsState.value = it
            }
        }
    }
}