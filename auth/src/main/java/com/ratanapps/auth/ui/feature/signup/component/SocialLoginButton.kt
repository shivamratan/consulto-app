package com.ratanapps.auth.ui.feature.signup.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ratanapps.auth.R

@Composable
fun SocialLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isGoogle: Boolean = false
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val iconRes = if (isGoogle) R.drawable.ic_google else R.drawable.ic_facebook
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = if (isGoogle) "Google" else "Facebook",
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
    }
}
