package com.example.examenretrofit.Dialogs

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
fun CreatePokemonDialog(
    onDismiss: () -> Unit,
    onConfirm: (Pokemon) -> Unit
) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear Pokémon") },
        text = {
            Column {
                OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID") })
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Peso") })
                OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Altura") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val pokemon = Pokemon(
                    id = id.toIntOrNull() ?: 0,
                    name = name,
                    type = type,
                    weight = weight.toIntOrNull() ?: 0,
                    height = height.toIntOrNull() ?: 0
                )
                onConfirm(pokemon)
                onDismiss()
            }) {
                Text("Crear")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}