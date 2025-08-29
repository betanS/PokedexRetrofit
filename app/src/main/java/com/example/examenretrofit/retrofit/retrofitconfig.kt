package com.example.examenretrofit.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @POST("api/Pokemon/CreatePokemon")
    suspend fun createPokemon(@Body pokemon: Pokemon): Response<Pokemon>
    @PUT("api/Pokemon/EditPokemon/{id}")
    suspend fun updatePokemon(@Path("id") id: Int, @Body pokemon: Pokemon): Response<Unit>
    @DELETE("api/Pokemon/DeletePokemon/{id}")
    suspend fun deletePokemon(@Path("id") id: Int): Response<Unit>


}

data class Pokemon(
    val id: Int,
    val name: String,
    val type: String,
    val weight: Int,
    val height: Int
)
