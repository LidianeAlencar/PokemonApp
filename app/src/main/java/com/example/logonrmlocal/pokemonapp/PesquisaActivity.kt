package com.example.logonrmlocal.pokemonapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logonrmlocal.pokemonapp.api.PokemonAPI
import com.example.logonrmlocal.pokemonapp.model.Pokemon
import kotlinx.android.synthetic.main.activity_pesquisa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PesquisaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)

        btPesquisar.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val api = retrofit.create(PokemonAPI::class.java)
            api.getPokemon(etNumero.text.toString().toInt())
                    .enqueue(object : Callback<Pokemon>{
                        override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                            Toast.makeText(this@PesquisaActivity,
                                    t?.message,
                                    Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                            if(response?.isSuccessful == true){
                                val pokemon = response.body()
                                tvPokemon.text = pokemon?.nome
                            }else{
                                Toast.makeText(this@PesquisaActivity,
                                        "Deu ruim",
                                        Toast.LENGTH_LONG).show()
                            }
                        }
                    })
        }
    }
}
