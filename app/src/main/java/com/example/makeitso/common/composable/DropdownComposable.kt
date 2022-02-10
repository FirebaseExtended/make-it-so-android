package com.example.makeitso.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.makeitso.theme.BrightOrange

@Composable
@ExperimentalMaterialApi
fun DropdownContextMenu(options: List<String>) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        modifier = Modifier.wrapContentWidth(),
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        Icon(
            modifier = Modifier.padding(8.dp, 0.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More"
        )

        ExposedDropdownMenu(
            modifier = Modifier.width(180.dp),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(onClick = { isExpanded = false }) { //Callback to ViewModel
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun DropdownSelector(@StringRes label: Int, options: List<String>, selection: String) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(selection) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        modifier = Modifier.fillMaxWidth(),
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOption,
            onValueChange = { }, //Callback to ViewModel updating state
            label = { Text(stringResource(label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(isExpanded) },
            colors = dropdownColors()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = selectionOption
                        isExpanded = false
                    }
                ) { Text(text = selectionOption) }
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
private fun dropdownColors(): TextFieldColors {
    return ExposedDropdownMenuDefaults.textFieldColors(
        backgroundColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor =  Color.Transparent,
        trailingIconColor = BrightOrange,
        focusedTrailingIconColor = BrightOrange,
        focusedLabelColor = BrightOrange,
        unfocusedLabelColor = BrightOrange
    )
}
