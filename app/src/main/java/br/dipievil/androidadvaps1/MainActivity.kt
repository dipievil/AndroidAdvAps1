package br.dipievil.androidadvaps1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import br.dipievil.androidadvaps1.client.CurrencyClientApi

class MainActivity : AppCompatActivity() {

    private val currencyClientApi : CurrencyClientApi by lazy {
        CurrencyClientApi()
    }

   private lateinit var spFrom : Spinner
   private lateinit var spTo : Spinner
   private lateinit var btConvert : Button
   private lateinit var tvResult : TextView
   private lateinit var etValueFrom : EditText

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        btConvert = findViewById(R.id.btConverter)
        tvResult = findViewById(R.id.tvResult)
        etValueFrom = findViewById(R.id.etValueFrom)

        getCurrencies()

        btConvert.setOnClickListener{ convert() }
    }

    fun getCurrencies(){
        currencyClientApi.getCurrencies(
            onSuccess = { currencies ->
                var data = mutableListOf<String>()

                currencies?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }

                val posBRL = data.indexOf("brl")
                val posUSD = data.indexOf("usd")

                val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)

                spFrom.adapter = adapter
                spTo.adapter = adapter

                spFrom.setSelection(posBRL)
                spTo.setSelection(posUSD)
            },
            onFailure = { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        )
    }

    fun convert(){
        currencyClientApi.convert(
            spFrom.selectedItem.toString(),
            spTo.selectedItem.toString(),
            etValueFrom.text.toString(),
            onSuccess = { convertedValue ->
                tvResult.text = convertedValue
            },
            onFailure = { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        )
    }
}