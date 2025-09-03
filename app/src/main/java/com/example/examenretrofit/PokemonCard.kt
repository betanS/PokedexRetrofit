package com.example.examenretrofit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.examenretrofit.retrofit.Pokemon
import androidx.compose.ui.graphics.Brush

fun getTypeGradient(typeString: String): Brush {
    //Color de la tarjeta en base al tipo
    val typeColors = mapOf(
        "fire"     to Color(0xFFFFCDD2),
        "water"    to Color(0xFFBBDEFB),
        "grass"    to Color(0xFFC8E6C9),
        "electric" to Color(0xFFFFF9C4),
        "psychic"  to Color(0xFFE1BEE7),
        "rock"     to Color(0xFFD7CCC8),
        "ice"      to Color(0xFFE0F7FA),
        "dragon"   to Color(0xFFB39DDB),
        "dark"     to Color(0xFFB0BEC5),
        "fairy"    to Color(0xFFF8BBD0)
    )

    val types = typeString.lowercase().split("/").map { it.trim() }

    val colors = types.mapNotNull { typeColors[it] }

    return when (colors.size) {
        2 -> Brush.linearGradient(colors)
        1 -> Brush.linearGradient(listOf(colors[0], colors[0]))
        else -> Brush.linearGradient(listOf(Color.LightGray, Color.Gray))
    }
}
@Composable
fun PokemonCard(pokemon: Pokemon) {
    val backgroundBrush = getTypeGradient(pokemon.type)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(brush = backgroundBrush)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

        ) {
            //Las tarjetas en cuestion
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "NºPokedex: ${pokemon.id}",
                    style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Nombre: ${pokemon.name}",
                    style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Tipo: ${pokemon.type}",
                    style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Peso: ${pokemon.weight} kg",
                    style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Altura: ${pokemon.height} cm",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}