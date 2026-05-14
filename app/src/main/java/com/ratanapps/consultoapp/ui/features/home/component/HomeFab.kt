package com.ratanapps.consultoapp.ui.features.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HomeFab(onFabClick: () -> Unit = {}) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        onClick = onFabClick
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}