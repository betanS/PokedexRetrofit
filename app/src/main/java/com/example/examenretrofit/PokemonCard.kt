package com.example.examenretrofit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.examenretrofit.retrofit.Pokemon

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