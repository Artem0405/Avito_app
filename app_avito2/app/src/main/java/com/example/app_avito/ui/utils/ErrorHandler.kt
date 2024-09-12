package com.example.app_avito.utils

import android.content.Context
import android.widget.Toast
import com.example.app_avito.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorHandler {

    fun handleError(context: Context, throwable: Throwable) {
        val message = when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> context.getString(R.string.error_bad_request)
                    401 -> context.getString(R.string.error_unauthorized)
                    404 -> context.getString(R.string.error_not_found)
                    500 -> context.getString(R.string.error_server_error)
                    else -> context.getString(R.string.error_unknown)
                }
            }
            is SocketTimeoutException -> context.getString(R.string.error_timeout)
            is IOException -> context.getString(R.string.error_no_internet)
            else -> context.getString(R.string.error_unknown)
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}