package com.example.examenretrofit.retrofit

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PokemonApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5102/") // 👈 importante la barra al final
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    }
}
interface PokemonApi {
    @GET("api/Pokemon/GetPokemons")
    suspend fun getPokemons(): List<Pokemon>
}
data class Pokemon(
    val id: Int,
    val name: String,
    val type: String,
    val weight: Int,
    val height: Int
)
