package com.ratanapps.consultoapp.ui.features.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(onHamburgerClick: ()->Unit = {},
                  onThemeCheckChange: (Boolean)->Unit = {},
                  isDark: Boolean = false) {
    TopAppBar(
        title = {Text("Notes App")},
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondary),
        navigationIcon = {
            IconButton(
                onClick = onHamburgerClick
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            Switch(
                checked = isDark,
                onCheckedChange = onThemeCheckChange
            )
        }
    )
}