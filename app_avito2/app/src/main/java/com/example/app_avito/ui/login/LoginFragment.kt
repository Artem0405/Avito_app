package com.example.app_avito.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.app_avito.R
import com.example.app_avito.databinding.FragmentLoginBinding
import com.example.app_avito.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Если пользователь уже авторизован, переходим на экран списка продуктов
        if (viewModel.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            return
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Наблюдение за состоянием входа
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is Resource.Loading -> {
                        // Показать индикатор загрузки
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        // Скрыть индикатор загрузки
                        binding.progressBar.visibility = View.GONE
                        // Переход на экран списка продуктов
                        findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                    }
                    is Resource.Error -> {
                        // Скрыть индикатор загрузки
                        binding.progressBar.visibility = View.GONE
                        // Показать сообщение об ошибке
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {} // Или null
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}