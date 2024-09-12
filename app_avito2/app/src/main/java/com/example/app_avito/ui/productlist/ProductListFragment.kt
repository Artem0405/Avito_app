package com.example.app_avito.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_avito.R
import com.example.app_avito.databinding.FragmentProductListBinding
import com.example.app_avito.ui.adapter.ProductAdapter
import com.example.app_avito.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Обработка выбора категории
        binding.categoryChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val category = when (checkedId) {
                R.id.electronicsChip -> "electronics"
                R.id.jeweleryChip -> "jewelery"
                R.id.menClothingChip -> "men's clothing"
                R.id.womenClothingChip -> "women's clothing"
                else -> null // Все категории
            }
            viewModel.getProducts(category)
        }

        // Обработка выбора сортировки
        binding.sortChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val sort = when (checkedId) {
                R.id.ascendingChip -> "asc"
                R.id.descendingChip -> "desc"
                else -> null // Без сортировки
            }
            viewModel.getProducts(sort = sort)
        }

        lifecycleScope.launch {
            viewModel.productsState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val products = state.data
                        if (products != null) {
                            val adapter = ProductAdapter(products) { product ->
                                // Переход на экран описания продукта
                                val action = ProductListFragmentDirections
                                    .actionProductListFragmentToProductDetailsFragment(product.id)
                                findNavController().navigate(action)
                            }
                            binding.productsRecyclerView.adapter = adapter
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}