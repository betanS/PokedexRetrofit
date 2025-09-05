package com.example.examenretrofit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.examenretrofit.retrofit.Pokemon

@Composable
fun PokemonEditDialog(
    pokemon: Pokemon,
    onDismiss: () -> Unit,
    onConfirm: (Pokemon) -> Unit
) {
    var name by remember { mutableStateOf(pokemon.name) }
    var type by remember { mutableStateOf(pokemon.type) }
    var weight by remember { mutableStateOf(pokemon.weight.toString()) }
    var height by remember { mutableStateOf(pokemon.height.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val updatedPokemon = pokemon.copy(
                    name = name,
                    type = type,
                    weight = weight.toIntOrNull() ?: pokemon.weight,
                    height = height.toIntOrNull() ?: pokemon.height
                )
                onConfirm(updatedPokemon)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Editar Pokémon") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Peso") })
                OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Altura") })
            }
        }
    )
}