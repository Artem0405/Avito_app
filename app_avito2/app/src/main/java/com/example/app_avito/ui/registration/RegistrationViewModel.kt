package com.example.app_avito.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_avito.data.model.User
import com.example.app_avito.data.repository.UserRepository
import com.example.app_avito.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _registrationState = MutableStateFlow<Resource<User>?>(null)
    val registrationState: StateFlow<Resource<User>?> = _registrationState

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = Resource.Loading()
            val user = User(name = name, email = email, password = password, cpassword = password)
            userRepository.registerUser(user).collect {
                _registrationState.value = it
            }
        }
    }
}