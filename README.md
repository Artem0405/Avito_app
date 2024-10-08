# Avito_app

# Тестовое задание: приложение интернет-магазина

## Описание

Это приложение является тестовым заданием и реализует базовый функционал интернет-магазина с использованием Fake Shop API.

## Функциональные требования

Приложение состоит из 4 экранов:

1. **Регистрация:** Позволяет пользователю зарегистрироваться, отправляя данные (имя, email, пароль, подтверждение пароля) на сервер.
2. **Вход:** Позволяет пользователю войти в систему, используя email и пароль. При успешном входе токен сохраняется локально.
3. **Список товаров:** Отображает список товаров, полученных от API. Предусмотрена фильтрация по категориям и сортировка по цене.
4. **Описание товара:** Отображает подробную информацию о выбранном товаре, включая название, категорию, цену, описание и изображение.

## Технические детали

**Язык:** Kotlin

**Архитектура:** MVVM

**Библиотеки:**

* Retrofit: для работы с сетевыми запросами
* OkHttp: HTTP клиент для Retrofit
* Kotlinx Serialization: для сериализации/десериализации JSON
* Coroutines: для асинхронных операций
* Jetpack Navigation: для навигации между фрагментами
* Coil: для загрузки изображений
* View Binding: для доступа к элементам разметки

## Инструкция по запуску

1. Склонируйте репозиторий.
2. Откройте проект в Android Studio.
3. Соберите и запустите приложение на эмуляторе или устройстве Android.

## Вопросы/проблемы и решения

* **Вопрос:** Как реализовать кэширование запросов?
  * **Решение:** Можно использовать OkHttp interceptor для кэширования ответов API.
* **Вопрос:** Как реализовать работу приложения в офлайн-режиме?
  * **Решение:** Можно сохранять данные о товарах в локальной базе данных (например, Room) и отображать их в офлайн-режиме.
* **Вопрос:** Как реализовать пагинацию?
  * **Решение:** API поддерживает пагинацию с помощью параметров `limit` и `page`. Можно добавить функционал для загрузки следующих страниц при прокрутке списка.

## Дополнительные замечания

* В данном приложении не реализована корзина, оформление заказа и другие функции полноценного интернет-магазина.
* Дизайн приложения максимально приближен к макетам, но может отличаться в деталях.
* Код приложения может быть улучшен и оптимизирован.

## Автор

Артем Краснов
