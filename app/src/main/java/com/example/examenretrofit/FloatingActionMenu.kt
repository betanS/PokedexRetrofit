package com.example.examenretrofit

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FabMenuDemo() {
    var isMenuOpen by remember { mutableStateOf(false) }

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
                FabMenuItem(icon = Icons.Default.Edit, label = "Modificar") { /* Acción */ }
                FabMenuItem(icon = Icons.Default.Delete, label = "Borrar") { /* Acción */ }
                FabMenuItem(icon = Icons.Default.Refresh, label = "Actualizar") { /* Acción */ }
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

