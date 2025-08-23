package com.example.examenretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.examenretrofit.retrofit.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PokemonScreen { fetchPokemons(it) }
            }
        }
    }

    private fun fetchPokemons(onResult: (List<Pokemon>) -> Unit) {
        lifecycleScope.launch {
            try {
                val pokemons = RetrofitInstance.api.getPokemons()
                onResult(pokemons)
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(emptyList())
            }
        }
    }
}

@Composable
fun PokemonScreen(onFetch: ((List<Pokemon>) -> Unit) -> Unit) {
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            onFetch { result ->
                pokemons = result
                // 👉 Logcat: imprime la lista completa
                result.forEach { pokemon ->
                    Log.d("POKEDEX", "ID: ${pokemon.id}, Nombre: ${pokemon.name}, Tipo: ${pokemon.type}, Peso: ${pokemon.weight}, Altura: ${pokemon.height}")
                }
            }
        }) {
            Text("Cargar Pokemons")
        }
    }
}