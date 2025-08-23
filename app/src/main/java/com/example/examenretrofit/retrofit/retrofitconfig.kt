package com.example.examenretrofit.retrofit

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PokemonApi by lazy { //asicrono
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5102/") //  puerto con 10.0.2.2 enfocado en el emulador,  en web sería el equivalente de localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }
}
interface PokemonApi {
    @GET("api/Pokemon/GetPokemons") // Ruta donde se encuentra el json
    suspend fun getPokemons(): List<Pokemon>
}
data class Pokemon( //Modelo de datos
    val id: Int,
    val name: String,
    val type: String,
    val weight: Int,
    val height: Int
)
