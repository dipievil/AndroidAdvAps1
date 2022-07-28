package br.dipievil.androidadvaps1.utils

import br.dipievil.androidadvaps1.services.CurrencyServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://cdn.jsdelivr.net";

class ApiUtils {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val currencyServices = retrofit.create(CurrencyServices::class.java);
}