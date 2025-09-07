package com.example.examenretrofit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.examenretrofit.retrofit.Pokemon
import com.example.examenretrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun PokemonScreen(onFetch: ((List<Pokemon>) -> Unit) -> Unit) {
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val refreshCallback = rememberUpdatedState(newValue = onFetch)
    val onRefresh = {
        Log.d("POKEDEX", "Refrescando lista de Pokémon")
        refreshCallback.value { result -> pokemons = result }
    }

    fun updatePokemon(pokemon: Pokemon) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.updatePokemon(pokemon.id, pokemon)
                if (response.isSuccessful) {
                    Log.d("POKEDEX", "Pokémon actualizado correctamente")
                    onRefresh()
                } else {
                    Log.e("POKEDEX", "Error al actualizar: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("POKEDEX", "Excepción al actualizar: ${e.message}")
            }
        }
    }

    var editingPokemon by remember { mutableStateOf<Pokemon?>(null) }

    editingPokemon?.let { pokemon ->
        PokemonEditDialog(pokemon = pokemon,
            onDismiss = { editingPokemon = null },
            onConfirm = { updated ->
                updatePokemon(updated)
            }
        )
    }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Button( modifier =
                    Modifier.height(120.dp)
                        .width(250.dp)
                        .padding(top = 60.dp),
                    colors =  ButtonDefaults.buttonColors(Color(0xFF43A047)
                ), onClick = {
                        onRefresh()
                }) {
                    Text("Cargar Pokemons")
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(pokemons.sortedBy { it.id }) { pokemon ->
                        PokemonCard(pokemon) { selected ->
                            editingPokemon = selected
                        }
                    }
                }
        }
    FabMenuDemo(onRefresh = onRefresh)
}