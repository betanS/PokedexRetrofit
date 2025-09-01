package com.example.examenretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.lifecycle.lifecycleScope
import com.example.examenretrofit.retrofit.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {                                     //REFACTORIZAR
                //Llamada a PokemonScreen (pagina principal)
                PokemonScreen { fetchPokemons(it) }
            }
        }
    }

    //Recoge los pokemons
    private fun fetchPokemons(onResult: (List<Pokemon>) -> Unit) {
        lifecycleScope.launch {
            try {
                val pokemons = RetrofitInstance.api.getPokemons()
                android.util.Log.d("API", "Pokemons recibidos: $pokemons")
                onResult(pokemons)
            } catch (e: Exception) {
                android.util.Log.e("API", "Error al obtener pokemons: ${e.message}")
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }
}

