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

@Composable
fun DeletePokemonDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var id by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar Pokémon") },
        text = {
            Column {
                OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID del Pokémon") })
            }
        },
        confirmButton = {
            Button(onClick = {
                id.toIntOrNull()?.let {
                    onConfirm(it)
                    onDismiss()
                }
            }) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}