package br.dipievil.androidadvaps1.client

import br.dipievil.androidadvaps1.services.CurrencyServices
import br.dipievil.androidadvaps1.utils.ApiUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyClientApi(val currencyService: CurrencyServices = ApiUtils().currencyServices) {
    fun getCurrencies(onSuccess: (currencies : JsonObject?) -> Unit,
                      onFailure: (error: String?) -> Unit)
    {
        currencyService.getCurrencies().enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                onSuccess(response.body())
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                onFailure(t.message)
            }
        })
    }

    fun convert(currencyFrom : String,
                currencyTo : String,
                onSuccess : (convertedValue : String?) -> Unit,
                onFailure : (error : String?) -> Unit){
        currencyService.getCurrencyRate(currencyFrom,currencyTo).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val data = response.body()?.entrySet()?.find { it.key == currencyTo }
                val rate : Double = data?.value.toString().toDouble()
                val conversion = currencyFrom.toDouble() * rate

                onSuccess(conversion.toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                onFailure(t.message)
            }
        })
    }
}