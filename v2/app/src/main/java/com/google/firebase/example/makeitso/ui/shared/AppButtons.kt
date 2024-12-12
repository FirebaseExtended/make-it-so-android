package com.google.firebase.example.makeitso.ui.shared

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.example.makeitso.R
import com.google.firebase.example.makeitso.ui.theme.DarkBlue

@Composable
fun AuthWithGoogleButton(@StringRes label: Int, onButtonClick: () -> Unit) {
   OutlinedButton(
       modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
       onClick = onButtonClick,
       colors = ButtonDefaults.outlinedButtonColors(
           containerColor = Color.White,
           contentColor = DarkBlue
       ),
       border = BorderStroke(1.dp, DarkBlue)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_g),
            modifier = Modifier.padding(horizontal = 16.dp),
            contentDescription = "Google logo"
        )

        Text(
            text = stringResource(label),
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}

@Composable
fun StandardButton(@StringRes label: Int, onButtonClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        onClick = onButtonClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = DarkBlue,
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, DarkBlue)
    ) {
        Text(
            text = stringResource(label),
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}