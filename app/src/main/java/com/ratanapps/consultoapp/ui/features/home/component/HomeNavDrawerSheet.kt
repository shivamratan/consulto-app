package com.ratanapps.consultoapp.ui.features.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ratanapps.consultoapp.R

@Composable
fun HomeNavDrawerSheet(onLogoutClicked: ()->Unit) {
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7f)) {

        Box(modifier = Modifier.fillMaxWidth().height(130.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "App Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.2f
            )

            Column {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_avatar),
                            contentDescription = "App Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(55.dp)
                                .height(55.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
                            Text(
                                text = "Shivam Ratan",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "shivamratan61@gmail.com",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(2.dp))

                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }


        }

        Spacer(modifier = Modifier.height(10.dp))


        Text(text = "Notes App",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        NavigationDrawerItem(
            label = { Text("Home") },
            selected = false,
            onClick = {  }
        )

        Spacer(modifier = Modifier.height(5.dp))
        NavigationDrawerItem(
            label = { Text("Logout") },
            selected = false,
            onClick = onLogoutClicked
        )
    }
}