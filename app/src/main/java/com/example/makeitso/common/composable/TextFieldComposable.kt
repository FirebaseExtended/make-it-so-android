package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
fun BasicField(@StringRes text: Int, icon: ImageVector? = null, initialState: String = "") {
    val fieldState = remember { mutableStateOf(initialState) }

    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.fieldModifier(),
        value = fieldState.value,
        onValueChange = { fieldState.value = it },
        placeholder = { Text(stringResource(text)) },
        leadingIcon = if (icon != null) {
            { Icon(imageVector = icon, contentDescription = "Name") }
        } else null
    )
}

@Composable
fun EmailField(initialState: MutableState<String>) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier.fieldModifier(),
        value = initialState.value,
        onValueChange = { initialState.value = it },
        placeholder = { Text(stringResource(AppText.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun PasswordField(initialState: MutableState<String>) {
    var isVisible by remember { mutableStateOf(false) }

    val icon = if (isVisible) painterResource(AppIcon.ic_visibility_on)
    else painterResource(AppIcon.ic_visibility_off)

    val visualTransformation = if (isVisible) VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = Modifier.fieldModifier(),
        value = initialState.value,
        onValueChange = { initialState.value = it },
        placeholder = { Text(text = stringResource(AppText.password)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

private fun Modifier.fieldModifier(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 4.dp)
}
