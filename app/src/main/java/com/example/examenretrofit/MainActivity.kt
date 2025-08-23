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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(
            modifier =
                Modifier.height(100.dp).width(250.dp).padding(top = 60.dp), colors =  ButtonDefaults.buttonColors(Color(0xFF43A047)
        ), onClick = {
            onFetch { result ->
                pokemons = result

                //Logcat: imprime la lista completa
                result.forEach { pokemon ->
                    Log.d("POKEDEX", "ID: ${pokemon.id}, Nombre: ${pokemon.name}, Tipo: ${pokemon.type}, Peso: ${pokemon.weight}, Altura: ${pokemon.height}")
                }
            }
        }) {
            Text("Cargar Pokedex")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pokemons) { pokemon ->
                //Column asyncrono con las tarjetas
                PokemonCard(pokemon)
            }
        }
    }
}



@Composable
fun PokemonCard(pokemon: Pokemon) {
    //Color de la tarjeta en base al tipo
    val backgroundColor = when (pokemon.type.lowercase()) {
        "fire" -> Color(0xFFFFCDD2) //Rojo suave
        "water" -> Color(0xFFBBDEFB) //Azul suave
        "grass" -> Color(0xFFC8E6C9)//Verde suave              //Arreglar transparencia de color y multitipos (alomejor fade 2 colores??)
        "electric" -> Color(0xFFFFF9C4)//Amarillo suave
        else -> Color.LightGray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
            //Las tarjetas en cuestion
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "NºPokedex: ${pokemon.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Nombre: ${pokemon.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Tipo: ${pokemon.type}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Peso: ${pokemon.weight} kg", style = MaterialTheme.typography.bodySmall)
            Text(text = "Altura: ${pokemon.height} cm", style = MaterialTheme.typography.bodySmall)
        }
    }
}
