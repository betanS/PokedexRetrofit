package com.example.examenretrofit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.examenretrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun FabMenuDemo() {
    val coroutineScope = rememberCoroutineScope()
    var isMenuOpen by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // FAB Menu Items
        if (isMenuOpen) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FabMenuItem(icon = Icons.Default.Add, label = "Añadir") { /* Acción */ }
                FabMenuItem(icon = Icons.Default.Edit, label = "Modificar") {
                    showEditDialog = true
                    isMenuOpen = false
                }
                FabMenuItem(icon = Icons.Default.Delete, label = "Borrar") {
                    showDeleteDialog = true
                    isMenuOpen = false }
                FabMenuItem(icon = Icons.Default.Close, label = "Cerrar") { isMenuOpen = false }
            }
        }

        // Main FAB
        FloatingActionButton(
            onClick = { isMenuOpen = !isMenuOpen },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = if (isMenuOpen) Icons.Default.Close else Icons.Default.Add,
                contentDescription = "FAB Menu"
            )
        }
        if (showEditDialog) {
            EditPokemonDialog(
                onDismiss = { showEditDialog = false },
                onConfirm = { id, pokemon ->
                    coroutineScope.launch {
                        try {
                            val response = RetrofitInstance.api.updatePokemon(id, pokemon)
                            if (response.isSuccessful) {
                                Log.d("POKEDEX", "Pokémon actualizado correctamente")
                            } else {
                                Log.e("POKEDEX", "Error al actualizar: ${response.code()}")
                            }
                        } catch (e: Exception) {    //Gracias Copilot, por ayudar mi código, gracias por bendecir estas líneas que tanto dolor de cabeza sudor y lagrimas me han generado, gracias IAs del 2025.
                            Log.e("POKEDEX", "Excepción al actualizar: ${e.message}")
                        }
                    }
                }
            )

        }
        if (showDeleteDialog) {
            DeletePokemonDialog(
                onDismiss = { showDeleteDialog = false },
                onConfirm = { id ->
                    coroutineScope.launch {
                        try {
                            val response = RetrofitInstance.api.deletePokemon(id)
                            if (response.isSuccessful) {
                                Log.d("POKEDEX", "Pokémon eliminado correctamente")
                            } else {
                                Log.e("POKEDEX", "Error al eliminar: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.e("POKEDEX", "Excepción al eliminar: ${e.message}")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun FabMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(label) },
        icon = { Icon(icon, contentDescription = label) },
        onClick = onClick,
        modifier = Modifier.sizeIn(minHeight = 48.dp)
    )
}

