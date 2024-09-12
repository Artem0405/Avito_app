package com.example.app_avito

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {
    // Здесь можно добавить инициализацию, которая должна выполняться при запуске приложения
    // Например, инициализация библиотек, настройка логирования и т.д.
}