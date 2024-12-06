package com.google.firebase.example.makeitso.ui.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.example.makeitso.ui.theme.DarkBlue
import com.google.firebase.example.makeitso.ui.theme.LightYellow

@Composable
fun LoadingIndicator() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (loadingIndicator) = createRefs()

        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .constrainAs(loadingIndicator) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = DarkBlue,
            trackColor = LightYellow,
        )
    }
}