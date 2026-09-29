package ua.edu.chnu.kkn.pagercomponentexample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PagerComponentExample",
    ) {
        App()
    }
}