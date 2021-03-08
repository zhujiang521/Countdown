/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.countdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R

@Composable
fun Countdown() {

    val viewModel: CountdownViewModel = viewModel()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDialogText by rememberSaveable { mutableStateOf("Please enter the correct number of seconds") }

    if (showDialog) {
        FunctionalityNotAvailablePopup(showDialogText) { showDialog = false }
    }

    val focusRequester = remember { FocusRequester() }
    val secondState = remember { SecondState() }
    val showCountdown by viewModel.showCountdown.observeAsState()
    val isActive by viewModel.isActive.observeAsState()

    Scaffold(
        backgroundColor = colorResource(id = R.color.purple_200),
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Adds view to Compose
                TimeSelect(secondState, onImeAction = { focusRequester.requestFocus() })
                Button(
                    onClick = {
                        if (secondState.text.isBlank() || !secondState.isValid || isActive == true) {
                            showDialogText = "Please wait until the countdown is over"
                            showDialog = true
                            return@Button
                        }
                        viewModel.startCountdown(secondState.text.toInt())
                    }
                ) {
                    Text(text = "Countdown")
                }
                Text(
                    modifier = Modifier.padding(30.dp),
                    text = showCountdown ?: "Start",
                    style = MaterialTheme.typography.h3
                )
            }
        }
    )
}

/**
 * Display a popup explaining functionality not available.
 *
 * @param onDismiss (event) request the popup be dismissed
 */
@Composable
private fun FunctionalityNotAvailablePopup(text: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}
