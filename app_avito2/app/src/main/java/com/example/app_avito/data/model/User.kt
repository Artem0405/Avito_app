package com.example.app_avito.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("name") val name: String? = null,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("cpassword") val cpassword: String? = null // Только для регистрации
)