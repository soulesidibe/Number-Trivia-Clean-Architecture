package com.soulesidibe.numbertrivia.view.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Number Trivia")
                }
            )
        }
    ) {

    }
}

@Preview(name = "Main Screen", device = Devices.PIXEL_4)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}